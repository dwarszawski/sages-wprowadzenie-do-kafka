package com.sages.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.consumer.dto.DObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

// Handling exceptions with specified handler and with global exception handler
//@Service
public class GConsumer {

    private static final Logger log = LoggerFactory.getLogger(GConsumer.class);

    private ObjectMapper objectMapper = new ObjectMapper();


    // default behaviour to log exception and consume
    // ability to implement own error handler
    // first start with specific error handler
    // then define global error handler
    // then rethrow from local error handler to global error handler
    // retry mechanism - service not available situation
    // retry n times
    @KafkaListener(topics = "g_topic", errorHandler = "gErrorHandler")
    public void consume(String message) throws IOException {
        var object = objectMapper.readValue(message, DObject.class);

        if (object.getDescription().contains("invalid")) {
            throw new IllegalArgumentException("Invalid object");
        }

    }

}
