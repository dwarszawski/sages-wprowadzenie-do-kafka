package com.sages.consumer.consumer;

import java.io.IOException;

import com.sages.consumer.dto.DObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class DConsumer {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(DConsumer.class);

	// possibility to override consumer factory
	// https://www.stackchief.com/blog/Spring%20Boot%20Kafka%20Consumer%20JSON%20Example
	@KafkaListener(topics = "d_topic")
	public void consume(String message) throws IOException {
		var object = objectMapper.readValue(message, DObject.class);
		log.info("Employee is {}", object);
	}

}
