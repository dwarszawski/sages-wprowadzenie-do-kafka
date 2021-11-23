package com.dwarszawski.connector.model;

import org.apache.kafka.connect.data.*;


public class SchemaUpdatedEvent {

    public static final String SUBJECT = "Subject";
    public static final String SCHEMA_ID = "SchemaId";
    public static final String SCHEMA = "Schema";
    public static final String VERSION = "Version";


    private String subject;
    private Integer schemaId;
    private Integer version;
    private String schema;

    public SchemaUpdatedEvent(String subject,
                              Integer schemaId,
                              String schema,
                              Integer version) {
        this.subject = subject;
        this.schemaId = schemaId;
        this.schema = schema;
        this.version = version;
    }


    public static final Schema SCHEMA_UPDATED_EVENT_SCHEMA = SchemaBuilder.struct()
            .name(SchemaUpdatedEvent.class.getSimpleName())
            .field(SUBJECT, Schema.STRING_SCHEMA)
            .field(SCHEMA_ID, Schema.INT32_SCHEMA)
            .field(SCHEMA, Schema.STRING_SCHEMA)
            .field(VERSION, Schema.INT32_SCHEMA)

            .build();

    public Struct toStruct() {
        Struct struct = new Struct(SCHEMA_UPDATED_EVENT_SCHEMA)
                .put(SUBJECT, getSubject())
                .put(SCHEMA_ID, getSchemaId())
                .put(SCHEMA, getSchema())
                .put(VERSION, getVersion());

        return struct;
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public Integer getVersion() {
        return version;
    }

    public String getSubject() {
        return subject;
    }

    public String getSchema() {
        return schema;
    }
}
