package com.sages.stream.extractors;

import org.apache.kafka.clients.consumer.ConsumerRecord;


public class RecordTimestampExtractor implements org.apache.kafka.streams.processor.TimestampExtractor {


    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        return 0;
    }
}

