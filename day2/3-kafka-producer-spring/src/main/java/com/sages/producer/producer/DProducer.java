package com.sages.producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sages.producer.dto.DObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Remove manual serialization and set it up with application.yaml
//@Service
public class DProducer {

	@Autowired
	private KafkaTemplate<String, DObject> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	//spring:
	//  kafka:
	//    producer:
	//      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
	public void send(DObject emp) {
		kafkaTemplate.send("d_topic", emp);
	}

}
