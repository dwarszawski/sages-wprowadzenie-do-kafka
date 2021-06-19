package com.sages.producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// Simple producer implementation
@Service
public class AProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String name) {
		kafkaTemplate.send("a_topic", "greetings " + name);
	}

}
