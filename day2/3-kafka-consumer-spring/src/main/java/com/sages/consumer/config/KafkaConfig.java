package com.sages.consumer.config;

import com.sages.consumer.exception.handler.GlobalErrorHandler;
import com.sages.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import java.time.LocalDateTime;

import java.util.Map;

@Configuration
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    //@Bean
    public ConsumerFactory<Long, Transaction> consumerFactory() {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        //time before enforcing metadata fetch request - enable to discover new partition or brokers
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "600000");

        return new DefaultKafkaConsumerFactory<>(properties);
    }


    @Bean(name = "consumerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Transaction> farLocationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<Long, Transaction>();

        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new GlobalErrorHandler());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // record filter strategy of filter
        factory.setRecordFilterStrategy(new RecordFilterStrategy<Long, Transaction>() {

            @Override
            public boolean filter(ConsumerRecord<Long, Transaction> consumerRecord) {
                Transaction transaction = consumerRecord.value();
                    return transaction.getDate().isBefore(LocalDateTime.now().minusDays(7));
            }
        });

        return factory;
    }

    // handling transient errors
    // retry mechanism before global error handler is invoked
    // Exponential Backoff to the Rescue
    // keep retyring until succeeded or retry count exceed
    //The idea behind using exponential backoff with retry is that instead of retrying after waiting for a fixed amount of time, we increase the waiting time between reties after each retry failure.
    @Bean(value = "retryContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Transaction> retryContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<Long, Transaction>();

        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new GlobalErrorHandler());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3);
        retryTemplate.setRetryPolicy(retryPolicy);


        FixedBackOffPolicy backoffPolicy = new FixedBackOffPolicy();
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
    public ConcurrentKafkaListenerContainerFactory<Long, Transaction> deadLetterContainerFactory(KafkaTemplate<Long, Transaction> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<Long, Transaction>();

        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // same partition as failed record
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new TopicPartition("transactions_dead_letters", record.partition()));

        // message which failed - no ack
        // try retry before sending dead letter
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(5_000, 2));
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

}
