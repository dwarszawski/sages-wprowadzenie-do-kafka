package com.sages.templates;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class AdvancedProducer {

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = createKafkaProducer();

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello world");

        producer.send(record);

        producer.flush();
        producer.close();

    }

    public static KafkaProducer<String, String> createKafkaProducer() {
        String bootstrapServers = "127.0.0.1:9092";

        Properties properties = new Properties();
        // TODO setup producer properties

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }
}


