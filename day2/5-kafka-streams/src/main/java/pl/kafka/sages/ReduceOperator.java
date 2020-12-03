package pl.kafka.sages;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

/**
 * Wykorzystanie operacji `reduce` do zliczania sumy dla strumienia liczb.
 */
public class ReduceOperator {

  static final String SUM_TOPIC = "sum-output";
  static final String INPUT_NUMBERS_TOPIC = "test1";

  public static void main(final String[] args) {
    final String bootstrapServers = args.length > 0 ? args[0] : "localhost:9092";
    final Properties streamsConfiguration = new Properties();

    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "reduce-operator-app");

    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
    streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");

    // Records should be flushed every 10 seconds. This is less than the default in order to keep this example interactive.
    streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);

    final Topology topology = getTopology();
    final KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

    streams.cleanUp();
    streams.start();

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

  static Topology getTopology() {
    final StreamsBuilder builder = new StreamsBuilder();

    final KStream<Integer, Integer> input = builder.stream(INPUT_NUMBERS_TOPIC);

    final KTable<Integer, Integer> sumOfOddNumbers = input
            .filter((k, v) -> v % 2 != 0)
      // We want to compute the total sum across ALL numbers, so we must re-key all records to the same key.
      .selectKey((k, v) -> 1)
      .groupByKey()
      .reduce((v1, v2) -> v1 + v2);

    sumOfOddNumbers.toStream().to(SUM_TOPIC);

    return builder.build();
  }

}
