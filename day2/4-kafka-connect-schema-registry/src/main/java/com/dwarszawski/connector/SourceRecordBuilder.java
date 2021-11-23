package com.dwarszawski.connector;

import com.dwarszawski.connector.model.SchemaUpdatedEvent;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.Collections;
import java.util.Map;

public class SourceRecordBuilder {


    private final SchemaUpdatedEvent request;
    private final String topic;
    private final String key;
    private Map<String, Object> partition;
    private Map<String, Object> offset;

    public SourceRecordBuilder(SchemaUpdatedEvent request, String topic, String key) {
        this.request = request;
        this.topic = topic;
        this.key = key;
    }

    public SourceRecord build() {
        return new SourceRecord(partition, offset, topic, Schema.STRING_SCHEMA, key, SchemaUpdatedEvent.SCHEMA_UPDATED_EVENT_SCHEMA, request.toStruct());
    }

    public SourceRecordBuilder withPartition(String key, String value) {
        this.partition = Collections.singletonMap(key, value);
        return this;
    }

    public SourceRecordBuilder withOffset(Map<String, Object> offset) {
        this.offset = offset;
        return this;
    }
}
