package com.sages.consumer.exception.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service(value = "customErrorHandler")
public class CustomErrorHandler implements ConsumerAwareListenerErrorHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomErrorHandler.class);

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		log.info("Handling error : {}, message : {}", message.getPayload(), exception.getMessage());

		// to rethrow to global error handler
		if (exception.getCause() instanceof RuntimeException) {
			throw exception;
		}
		return null;
	}

}
