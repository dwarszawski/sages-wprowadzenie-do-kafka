package com.sages.consumer.config;

import com.sages.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

//@Configuration
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    //@Bean
    public ConsumerFactory<Long, Transaction> consumerFactory() {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        //time before enforcing metadata fetch request - enable to discover new partition or brokers
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "600000");

        return new DefaultKafkaConsumerFactory<>(properties);
    }
}
