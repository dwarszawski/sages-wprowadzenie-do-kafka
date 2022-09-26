package com.sages.stream.extractors;

import com.sages.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class RecordTimestampExtractor implements org.apache.kafka.streams.processor.TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        Transaction message = (Transaction) record.value();

        return message != null ? toEpochTimestamp(message.getDate())
                : record.timestamp();
    }

    public long toEpochTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}

