package com.sages.producer.producer;

import com.sages.producer.dto.DObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Ability to filter messages with overriding kafka listener container factory
//@Service
public class FProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void send(DObject object) throws JsonProcessingException {
			var json = objectMapper.writeValueAsString(object);
			kafkaTemplate.send("f_topic", object.getId(), json);
	}

}
