package com.sages.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.consumer.dto.DObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

// Ability to filter messages with overriding kafka listener container factory
//@Service
public class FConsumer {

	private static final Logger log = LoggerFactory.getLogger(FConsumer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@KafkaListener(topics = "f_topic", groupId = "f_group")
	public void listenAll(String message) throws IOException {
		var dObject = objectMapper.readValue(message, DObject.class);
		log.info("listenAll() : {}", dObject);
	}

	@KafkaListener(topics = "f_group", groupId = "f_group", containerFactory = "fContainerFactory")
	public void listenFar(String message) throws IOException {
		var dObject = objectMapper.readValue(message, DObject.class);
		log.info("listenFiltered() : {}", dObject);
	}

}
