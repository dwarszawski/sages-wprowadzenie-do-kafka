package com.sages.producer.producer;

import com.sages.schema.Transaction1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class AvroProducer {

    @Autowired
    private KafkaTemplate<Long, Transaction1> kafkaTemplate;

    public void send(Transaction1 transaction) {
        kafkaTemplate.send("transactions", transaction.getId(), transaction);
    }

}

