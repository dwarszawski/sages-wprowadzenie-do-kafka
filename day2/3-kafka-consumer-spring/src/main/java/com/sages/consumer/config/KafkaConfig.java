package com.sages.consumer.config;

import java.io.IOException;
import java.time.LocalDate;

import com.sages.consumer.dto.DObject;
import com.sages.consumer.exception.handler.GlobalGErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.SeekUtils;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

//@Configuration
public class KafkaConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		var properties = kafkaProperties.buildConsumerProperties();
		//time before discovering new partition
		properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "600000");

		return new DefaultKafkaConsumerFactory<>(properties);
	}


	@Bean(name = "fContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());


		// record filter strategy of filter
		factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

			private ObjectMapper objectMapper = new ObjectMapper();
			
			@Override
			public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
				try {
					var dObject = objectMapper.readValue(consumerRecord.value().toString(), DObject.class);
					return dObject.getDate().isAfter(LocalDate.now().minusDays(7));
				} catch (IOException e) {
					return false;
				}
			}
		});

		return factory;
	}

	// global error handler can be override with consumer container attribute

	// to direct from specific error handler to global error handler requires to rethrow exception from specific error handler

	@Bean(value = "globalExceptionHandlerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		factory.setErrorHandler(new GlobalGErrorHandler());

		return factory;
	}

	// handling transient errors
	// retry mechanism before global error handler is invoked
	// Exponential Backoff to the Rescue
	// keep retyring until succeeded or retry count exceed
	//The idea behind using exponential backoff with retry is that instead of retrying after waiting for a fixed amount of time, we increase the waiting time between reties after each retry failure.
	@Bean(value = "retryContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> retryContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		factory.setErrorHandler(new GlobalGErrorHandler());

		var retryTemplate = new RetryTemplate();

		var retryPolicy = new SimpleRetryPolicy(3);
		retryTemplate.setRetryPolicy(retryPolicy);

		var backoffPolicy = new FixedBackOffPolicy();
		backoffPolicy.setBackOffPeriod(10000);
		retryTemplate.setBackOffPolicy(backoffPolicy);

		factory.setRetryTemplate(retryTemplate);
		factory.setRecoveryCallback(new RecoveryCallback<Object>() {

			@Override
			public Object recover(RetryContext context) throws Exception {
				System.out.println("Recovery callback " + context.getRetryCount());
				return null;
			}
		});

		return factory;
	}


	// keep failing due to non-technical issue e.g. wrong data
	// permanent technical error - endpoint changed
	// ability to handle this case differently
	// spring supports mechanism to send message to dedicated topic
	// dead letter topic
	// combined with retry mechanism and produce to dead letter topic
	// dead letter record
	// dead letter topic must have at least same partition count as origin topic
	// default configuration - dead letter topic with .dlt extension - most of the time worth to overried the naming convention
	// scenario to handle: publish event -> throw exception in consumer process -> retry 5 times -> after 5 times publish event to dead letter topic
	// create topic manually - original and dead letter
	// console consumer for dead letter topic
	// dead letter topic - just regular topic
	// dead letter record - just regular record
	// create different consumer to process the message from dead letter topic

	@Bean(value = "deadLetterContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> deadLetterContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer, KafkaTemplate<Object, Object> kafkaTemplate) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		// same partition as failed record
		var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
				(record, ex) -> new TopicPartition("g_topic_dlt", record.partition()));

		// message which failed - no ack
		// try retry before sending dead letter
		var errorHandler = new SeekToCurrentErrorHandler(recoverer, new FixedBackOff(10_000, 3));
		factory.setErrorHandler(errorHandler);

		return factory;
	}

}
