package com.sages.producer.config;

import com.sages.schema.Transaction1;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

//@Configuration
public class AvroKafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    //@Bean
    public KafkaTemplate<Long, Transaction1> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<Long, Transaction1> producerFactory() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, "180000");

        return new DefaultKafkaProducerFactory<>(properties);
    }

}
