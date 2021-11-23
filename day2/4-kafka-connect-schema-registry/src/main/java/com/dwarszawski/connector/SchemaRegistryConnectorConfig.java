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
    return new ConfigDef()
            .define(SCHEMA_REGISTRY_URL, Type.STRING, "http://172.17.0.1:8081/", new EndpointValidator(), ConfigDef.Importance.HIGH, "schema registry url")
            .define(TOPIC_CONFIG, ConfigDef.Type.STRING, "schema.updates", new ConfigDef.NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, "name of the topic to produce to");

  }
}
