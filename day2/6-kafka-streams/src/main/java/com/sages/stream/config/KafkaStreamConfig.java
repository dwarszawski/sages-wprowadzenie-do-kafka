package com.sages.stream.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kafkaStreamConfig() {
		Map<String, Object> props = new HashMap<String, Object>();

		//mandatory configuration
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "transaction_processor");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "http://kafka-1:29092, http://kafka-2:39092, http://kafka-3:49092");

		// requires to run multiple instances running on the same filesystem
		//props.put(StreamsConfig.STATE_DIR_CONFIG, "C:\\tmp\\instance1");
		props.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/transaction_processor/instance2");


		return new KafkaStreamsConfiguration(props);
	}

}
