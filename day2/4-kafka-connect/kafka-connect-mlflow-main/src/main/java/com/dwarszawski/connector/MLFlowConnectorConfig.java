package com.dwarszawski.connector;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class MLFlowConnectorConfig extends AbstractConfig {

  public static final String SOURCE_TRACKING_URL = "source_tracking_url";
  public static final String TOPIC_CONFIG = "topic";

  public MLFlowConnectorConfig(Map<String, String> originals) {
    super(config(), originals);
  }

  public static ConfigDef config() {
    return new ConfigDef();
  }
}
