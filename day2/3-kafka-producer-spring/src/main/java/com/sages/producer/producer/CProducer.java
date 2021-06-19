package com.sages.producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// Sending message with key
// Scaling consumers
//@Service
public class CProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String key, String data) {
		kafkaTemplate.send("c_topic", key, data);
	}

}
