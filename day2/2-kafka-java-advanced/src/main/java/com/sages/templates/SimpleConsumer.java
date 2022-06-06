package com.sages.templates;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        String topic = "mytopic";

        KafkaConsumer<String, String> consumer = createConsumer(topic);

        while (true) {
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Key: " + record.key() + ", Value: " + record.value());
                System.out.println("Partition: " + record.partition() + ", Offset:" + record.offset());
                System.out.println("Timestamp: " + record.timestamp());
            }
            consumer.commitSync();
        }
    }

    public static KafkaConsumer<String, String> createConsumer(String topic) {

        String bootstrapServers = "http://{gateway-address}:29092,http://{gateway-address}:39092,http://{gateway-address}:49092";
        String groupId = "kafka-advanced-consumer";

        Properties properties = new Properties();
        // TODO setup consumer properties

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));

        return consumer;

    }

}
