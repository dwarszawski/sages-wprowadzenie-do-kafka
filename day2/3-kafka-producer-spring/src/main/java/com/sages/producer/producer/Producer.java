package com.sages.producer.producer;

import com.sages.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class Producer {

    @Autowired
    private KafkaTemplate<Long, Transaction> kafkaTemplate;

    public void send(Transaction transaction) throws JsonProcessingException {
        kafkaTemplate.send("transactions", transaction.getId(), transaction);
    }

}

