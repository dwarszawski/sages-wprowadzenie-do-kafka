package com.sages.consumer.consumer;

import com.sages.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

//@Service
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    // default behaviour to log exception and consume
    // ability to implement own error handler
    // first start with specific error handler
    // then define global error handler
    // then rethrow from local error handler to global error handler
    // retry mechanism - service not available situation
    // retry n times

    //@Autowired
    //private ObjectMapper objectMapper

    //@KafkaListener(topics = "transactions", containerFactory = "consumerContainerFactory", errorHandler = "customErrorHandler")
    @KafkaListener(topics = "transactions", containerFactory = "deadLetterContainerFactory", errorHandler = "customErrorHandler")
    public void consume(Transaction transaction, Acknowledgment ack) {
        //var object = objectMapper.readValue(message, Transaction.class);

        log.info("next transaction received");

        if (!transaction.getDescription().equals("DEBIT") && !transaction.getDescription().equals("CREDIT")) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        ack.acknowledge();
        log.info("transaction: " + transaction.getId()  + " with timestamp: " + transaction.getDate() +  " acknowledged");
    }

}
