package com.sages.producer.producer;

import com.sages.producer.dto.DObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Handling exceptions on consumer side
@Service
public class GProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    // handling exceptions
    // simplest way to use try catch block
    public void send(DObject object) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(object);
        kafkaTemplate.send("g_topic", object.getId(), json);
    }

}

