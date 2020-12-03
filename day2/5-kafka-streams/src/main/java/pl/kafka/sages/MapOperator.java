package pl.kafka.sages;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

/**
 * Przykład wykorzystania funkcji `map`
 * Zastosowanie Use cases include e.g. normalizacja lub anonimizacja danych
 */

public class MapOperator {

  // Dedykowane serializatory
  private final static Serde<String> stringSerde = Serdes.String();
  private final static Serde<byte[]> byteArraySerde = Serdes.ByteArray();

  private final static String bootstrapServers = "localhost:9092";


  public static void main(final String[] args) {

    final Properties streamsConfiguration = new Properties();

    // Unikalna nazwa w ramach klastra Kafka
    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "map-operator-app");
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "map-operator-client");
    // Adresy brokerów
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    // Domyślne serializowanie i deserializowanie rekordów
    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    final StreamsBuilder builder = new StreamsBuilder();

    final KStream<byte[], String> textLines = builder.stream("input-text", Consumed.with(byteArraySerde, stringSerde));

    // Variant 1: using `mapValues`
    final KStream<byte[], String> uppercasedWithMapValues = textLines.mapValues(v -> v.toUpperCase());


    // In this case we can rely on the default serializers for keys and values
    uppercasedWithMapValues.to("uppercased-text");

    // Variant 2: using `map`, modify value only (equivalent to variant 1)
    final KStream<byte[], String> uppercasedWithMap = textLines.map((key, value) -> new KeyValue<>(key, value.toUpperCase()));

    final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
    // Always (and unconditionally) clean local state prior to starting the processing topology.
    // The drawback of cleaning up local state prior is that your app must rebuilt its local state from scratch, which
    // will take time and will require reading all the state-relevant data from the Kafka cluster over the network.

    streams.cleanUp();
    streams.start();

    // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }

}
