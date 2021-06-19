package com.sages.producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

// sending messages with fixed rate intervals
//@Service
public class BProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private Logger log = LoggerFactory.getLogger(BProducer.class);

	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		var timestamp = System.currentTimeMillis();
		log.info("Sending another message " + timestamp);
		kafkaTemplate.send("b_topic", "messages with timestamp " + timestamp );
	}

}
