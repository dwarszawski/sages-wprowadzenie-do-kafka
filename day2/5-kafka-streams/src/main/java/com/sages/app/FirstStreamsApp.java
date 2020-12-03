package com.sages.app;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Arrays;
import java.util.Properties;

public class FirstStreamsApp {
    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "first-streams-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "100");

        // TODO build stream topology
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream("my-second-topic");

        KStream counts = stream
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")))
                .map((key, value) -> new KeyValue<Object, Object>(value, value))
                .groupByKey()
                .count()
                .mapValues(value -> Long.toString(value))
                .toStream();

        counts.to("word-count");
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), config);

        kafkaStreams.start();

    }
}
