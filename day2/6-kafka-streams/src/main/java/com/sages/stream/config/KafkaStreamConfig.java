package com.sages.stream.config;

import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kafkaStreamConfig() {
		var props = new HashMap<String, Object>();

		//mandatory configuration
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream1");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.1:29092");

		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass().getName());

		props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "3000");

		// exactly once once configuration
		// can be configured with applications yamls
		//props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
		//props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

		return new KafkaStreamsConfiguration(props);
	}

}
