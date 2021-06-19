package com.sages.consumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AConsumer {

	// group id taken from application.yml
	@KafkaListener(topics = "a_topic")
	public void consume(String message) {
		System.out.println(message);
	}

}
