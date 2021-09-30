package com.sages.consumer.config;

import com.sages.consumer.exception.handler.GlobalErrorHandler;
import com.sages.schema.Transaction1;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

//@Configuration
public class AvroKafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(AvroKafkaConfig.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Long, Transaction1> consumerFactory() {
        var properties = kafkaProperties.buildConsumerProperties();
        //time before enforcing metadata fetch request - enable to discover new partition or brokers
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "600000");
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(properties);
    }


    @Bean(name = "consumerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Transaction1> farLocationContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, Transaction1>();

        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new GlobalErrorHandler());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }

}
