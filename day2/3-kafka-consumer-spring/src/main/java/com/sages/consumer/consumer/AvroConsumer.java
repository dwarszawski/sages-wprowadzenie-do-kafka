package com.sages.consumer.consumer;

import com.sages.schema.Transaction1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

//@Service
public class AvroConsumer {

    private static final Logger log = LoggerFactory.getLogger(AvroConsumer.class);

    @KafkaListener(topics = "avro.transactions", containerFactory = "consumerContainerFactory")
    public void consume(Transaction1 transaction, Acknowledgment ack) {

        log.info("next transaction received");

        if (!transaction.getDescription().toString().equals("DEBIT") && !transaction.getDescription().toString().equals("CREDIT")) {
            throw new IllegalArgumentException("Invalid transaction type: " + transaction.getDescription());
        }
        ack.acknowledge();
        log.info("transaction: " + transaction.getId()  + " with timestamp: " + transaction.getDate() +  " acknowledged");
    }

}
