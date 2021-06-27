package com.sages.consumer.exception.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;

public class GlobalErrorHandler implements ConsumerAwareErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        // all errors logged to std out
        // having global error handler instead of defining that on each listener
        // requires to register error handler into kafka consumer container factory
        log.warn("Global error handler for message {}", thrownException.getMessage());
    }

}
