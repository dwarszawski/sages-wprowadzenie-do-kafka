package com.sages.producer.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sages.model.Transaction;
import com.sages.producer.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Scheduler {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private Producer producer;

    @Scheduled(fixedRate = 5000)
    public void generateTransaction() throws JsonProcessingException {
        Transaction first = new Transaction(generateTransactionId(), 10.00, "DEBIT", LocalDateTime.now());
        Transaction second = new Transaction(generateTransactionId(), 2000.00, "CREDIT", LocalDateTime.now());
        Transaction third = new Transaction(generateTransactionId(), 100.00 ,"DEBIT", LocalDateTime.now());
        Transaction invalid = new Transaction(generateTransactionId(),  5.25, "invalid", LocalDateTime.now());

        first.setDate(LocalDateTime.now().minusWeeks(2));


        producer.send(first);
        producer.send(second);
        producer.send(third);
        //producer.send(invalid);

        log.info("Done");
    }

    private long generateTransactionId() {
        long leftLimit = 1L;
        long rightLimit = 1_000_000_000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

}
