package com.sages.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

//@Service
public class BConsumer {

	private static final Logger log = LoggerFactory.getLogger(BConsumer.class);

	@KafkaListener(topics = "b_topic")
	public void consume(String message) {
		log.info("Consuming : {}", message);
	}

}
