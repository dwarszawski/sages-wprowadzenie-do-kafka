package pl.kafka.sages;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

/**
 * Wykorzystanie operacji `group-by` i `aggregate` on KTable. In this specific example we
 * Grupowanie i zliczanie ilości użytkowników dla danego regionu.
 *
 *
 * kafka-console-producer --broker-list localhost:9092 --topic user-regions   --property "parse.key=true" --property "key.separator=:"
 * kafka-console-consumer --bootstrap-server localhost:9092 --topic popular-regions --from-beginning --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer --property print.key=true
 */
public class CountOperator {

  public static void main(final String[] args) {
    final String bootstrapServers = "localhost:9092";
    final Properties streamsConfiguration = new Properties();

    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "count-operator-app");
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "count-operator-client");
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);

    final Serde<String> stringSerde = Serdes.String();
    final Serde<Long> longSerde = Serdes.Long();

    final StreamsBuilder builder = new StreamsBuilder();

    final KTable<String, String> userRegions = builder.table("user-regions");

    // Aggregate the user counts of by region
    final KTable<String, Long> regionCounts = userRegions
      // Count by region
      .groupBy((userId, region) -> KeyValue.pair(region, region))
      .count()
      .filter((regionName, count) -> count >= 2);

    // Note: The following operations would NOT be needed for the actual users-per-region
    // computation, which would normally stop at the filter() above.  We use the operations
    // below only to "massage" the output data so it is easier to inspect on the console via
    // kafka-console-consumer.
    //
    final KStream<String, Long> regionCountsForConsole = regionCounts
      // get rid of windows (and the underlying KTable) by transforming the KTable to a KStream
      .toStream()
      // sanitize the output by removing null record values (again, we do this only so that the
      // output is easier to read via kafka-console-consumer combined with LongDeserializer
      // because LongDeserializer fails on null values, and even though we could configure
      // kafka-console-consumer to skip messages on error the output still wouldn't look pretty)
      .filter((regionName, count) -> count != null);

    regionCountsForConsole.to("popular-regions", Produced.with(stringSerde, longSerde));

    final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);

    streams.cleanUp();
    streams.start();

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

}
