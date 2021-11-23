package com.dwarszawski.connector;

import com.dwarszawski.connector.model.SchemaUpdateRequestFactory;
import com.dwarszawski.connector.model.SchemaUpdatedEvent;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SchemaRegistrySourceTask extends org.apache.kafka.connect.source.SourceTask {

    static final Logger log = LoggerFactory.getLogger(SchemaRegistrySourceTask.class);

    public static final String SCHEMA_REGISTRY_URL = "schema_registry_url";

    SchemaRegistryClient client;
    private String topic;
    private String url;
    Map<String, Object> offsets;

    @Override
    public String version() {
        return SchemaRegistrySourceTask.class.getPackage().getImplementationVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        final String schemaRegistryUrl = map.get(SchemaRegistryConnectorConfig.SCHEMA_REGISTRY_URL);

        this.client = new CachedSchemaRegistryClient(schemaRegistryUrl, 10);
        this.topic = map.get(SchemaRegistryConnectorConfig.TOPIC_CONFIG);
        this.url = schemaRegistryUrl;


        if (context != null && context.offsetStorageReader() != null) {
            offsets = context.offsetStorageReader().offset(Collections.singletonMap(SCHEMA_REGISTRY_URL, url));
        }

        if (offsets == null) {
            offsets = new HashMap<>();
        }
    }

    @Override
    public List<SourceRecord> poll() {
        List<SourceRecord> records = new ArrayList<>();

        try {
            final Collection<String> allSubjects = client.getAllSubjects();
            for (String subject : allSubjects) {
                final SchemaMetadata latestSchemaMetadata = client.getLatestSchemaMetadata(subject);
                offsets.forEach((k, v) -> System.out.println(k + ":" + v));
                System.out.println("Latest schema *********************" + latestSchemaMetadata.getVersion());
                if (latestSchemaMetadata.getVersion() > (int) offsets.getOrDefault(subject, -1)) {

                    offsets.put(subject, (Object) latestSchemaMetadata.getVersion());

                    SchemaUpdatedEvent schemaUpdateRequest = SchemaUpdateRequestFactory.newSchemaUpdateEvent(subject, latestSchemaMetadata);
                    SourceRecordBuilder builder = new SourceRecordBuilder(schemaUpdateRequest, this.topic, this.url)
                            .withPartition(SCHEMA_REGISTRY_URL, this.url)
                            .withOffset(offsets);

                    records.add(builder.build());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception *********************" + e.getMessage());
            // Exception *********************Invalid Java object for schema with type STRING: class java.lang.Integer for field: "Subject"
        }

        return records;
    }

    @Override
    public void stop() {
        //TODO: Do whatever is required to stop your task.
    }
}