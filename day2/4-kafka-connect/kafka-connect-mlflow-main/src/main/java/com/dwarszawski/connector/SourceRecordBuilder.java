package com.dwarszawski.connector;

import com.dwarszawski.connector.model.ModelExportRequest;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.Collections;
import java.util.Map;

public class SourceRecordBuilder {

    private final ModelExportRequest request;
    private final String topic;
    private final String key;
    private Map<String, Object> partition;
    private Map<String, Object> offset;

    public SourceRecordBuilder(ModelExportRequest request, String topic, String key, String partitionKey, String partitionValue, String offsetKey, String offsetValue) {
        this.partition = Collections.singletonMap(partitionKey, partitionValue);
        this.offset = Collections.singletonMap(offsetKey, offsetValue);
        this.request = request;
        this.topic = topic;
        this.key = key;
    }

    public SourceRecord build() {
        return new SourceRecord(partition, offset, topic, Schema.STRING_SCHEMA, key, ModelExportRequest.MODEL_EXPORT_SCHEMA, request.toStruct());
    }


}
