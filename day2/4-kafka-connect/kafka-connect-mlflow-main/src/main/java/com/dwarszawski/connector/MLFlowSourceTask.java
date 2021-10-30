package com.dwarszawski.connector;

import org.apache.kafka.connect.source.SourceRecord;
import org.mlflow.api.proto.ModelRegistry;
import org.mlflow.tracking.MlflowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MLFlowSourceTask extends org.apache.kafka.connect.source.SourceTask {

    static final Logger log = LoggerFactory.getLogger(MLFlowSourceTask.class);

    public static final String URL = "url";
    public static final String LAST_TIMESTAMP = "last_timestamp";
    public static final String PRODUCTION_STAGE = "Production";

    MlflowClient client;
    private String topic;
    private String url;
    long lastTimestamp = 0;

    @Override
    public String version() {
        return MLFlowSourceTask.class.getPackage().getImplementationVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        final String trackingUri = map.get(MLFlowConnectorConfig.SOURCE_TRACKING_URL);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        final List<ModelRegistry.ModelVersion> registeredModels = client.listRegisteredModels()
                .stream()
                .flatMap(rec -> rec.getLatestVersionsList().stream())
                .collect(Collectors.toList());

        registeredModels.sort(new ModelVersionComparator());

        List<SourceRecord> records = new ArrayList<>();

        return records;
    }

    @Override
    public void stop() {
        //TODO: Do whatever is required to stop your task.
    }
}