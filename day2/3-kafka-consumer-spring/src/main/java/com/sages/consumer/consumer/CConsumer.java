package com.sages.consumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

// setting multiple consumers with setting up concurrency
//@Service
public class CConsumer {

	private static final Logger log = LoggerFactory.getLogger(CConsumer.class);

	// examine the logs - different threads representing different consumers that handles different set of partitions
	// one consumer per each partition
	@KafkaListener(topics = "c_topic", concurrency = "3")
	public void consume(ConsumerRecord<String, String> message) throws InterruptedException {
		log.info("Key : {}, Partition : {}, Message : {}", message.key(), message.partition(), message.value());
		Thread.sleep(1000);
	}

}
