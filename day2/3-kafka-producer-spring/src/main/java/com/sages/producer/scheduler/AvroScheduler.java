package com.sages.producer.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sages.producer.producer.AvroProducer;
import com.sages.schema.Transaction1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AvroScheduler {

    private static final Logger log = LoggerFactory.getLogger(AvroScheduler.class);

    @Autowired
    private AvroProducer producer;

    @Scheduled(fixedRate = 5000)
    public void generateTransaction() throws JsonProcessingException {


        Transaction1 first = Transaction1.newBuilder().setId(generateTransactionId()).setValue(10.00).setDescription("DEBIT").setDate(LocalDateTime.now().toInstant(ZoneOffset.UTC)).build();
        producer.send(first);

        log.info("Done");
    }

    private long generateTransactionId() {
        long leftLimit = 1L;
        long rightLimit = 1_000_000_000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

}
