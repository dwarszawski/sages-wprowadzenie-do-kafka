package com.dwarszawski.connector.model;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

public class SchemaUpdateRequestFactory {
    public static SchemaUpdatedEvent newSchemaUpdateEvent(String subject, SchemaMetadata schemaMetadata) {
        return new SchemaUpdatedEvent(
                subject,
                schemaMetadata.getId(),
                schemaMetadata.getSchema(),
                schemaMetadata.getVersion()
        );
    }
}
