package com.dwarszawski.connector;

import com.dwarszawski.connector.validators.EndpointValidator;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;

import java.util.Map;

public class SchemaRegistryConnectorConfig extends AbstractConfig {

  public static final String SCHEMA_REGISTRY_URL = "schema_registry_url";
  public static final String TOPIC_CONFIG = "topic";

  public SchemaRegistryConnectorConfig(Map<String, String> originals) {
    super(config(), originals);
  }

  public static ConfigDef config() {
    return new ConfigDef();
      // TODO define connector config

  }
}
