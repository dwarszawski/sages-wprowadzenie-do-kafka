package com.sages.consumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

// Ability to rebalance consumers by changing the number of partitions
//@Service
public class EConsumer {

	private static final Logger log = LoggerFactory.getLogger(EConsumer.class);


	//
	//kafka-topics.sh --bootstrap-server localhost:9092 --create --topic e_topic --partitions 1 --replication-factor 1
	//
	//kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic e_topic --partitions 2
	// default behaviour of kafka consumer - no message is consumed from partition for some time (5 mins)
	// rebalancing automatically by seeting up  metaata.miliseconds
	// look for ConsumerCoordinator
	@KafkaListener(topics = "e_topic", concurrency = "3")
	public void consume(ConsumerRecord<String, String> message) throws InterruptedException {
		log.info("Partition : {}, Offset : {}, Message : {}", message.partition(), message.offset(), message.value());
	}

}
