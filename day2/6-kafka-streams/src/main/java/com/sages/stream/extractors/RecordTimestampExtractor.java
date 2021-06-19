package com.sages.stream.extractors;

import com.sages.stream.dto.CTransaction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class RecordTimestampExtractor implements org.apache.kafka.streams.processor.TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var message = (CTransaction) record.value();

        return message != null ? toEpochTimestamp(message.getTimestamp())
                : record.timestamp();
    }

    public long toEpochTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}

