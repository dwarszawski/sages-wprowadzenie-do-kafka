package com.sages.templates;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SimpleProducer {

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = createKafkaProducer();

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("sages", "hello world");

        producer.send(record);

        producer.flush();
        producer.close();

    }

    public static KafkaProducer<String, String> createKafkaProducer() {
        String bootstrapServers = "http://kafka-1:29092,http://kafka-2:39092,http://kafka-3:49092";

        Properties properties = new Properties();
        // TODO setup producer properties

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }
}


