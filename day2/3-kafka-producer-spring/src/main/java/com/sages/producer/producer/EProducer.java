package com.sages.producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class EProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private int i = 0;

	@Scheduled(fixedRate = 1000)
	public void sendMessage() {
		i++;
		kafkaTemplate.send("e_topic", "Counter " + i);
	}

}
