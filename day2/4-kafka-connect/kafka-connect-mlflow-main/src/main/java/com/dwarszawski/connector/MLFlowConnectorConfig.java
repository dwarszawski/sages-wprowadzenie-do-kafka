package com.dwarszawski.connector;

import com.dwarszawski.connector.validators.EndpointValidator;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;

import java.util.Map;

public class MLFlowConnectorConfig extends AbstractConfig {

  public static final String SOURCE_TRACKING_URL = "source_tracking_url";
  public static final String TOPIC_CONFIG = "topic";

  public MLFlowConnectorConfig(Map<String, String> originals) {
    super(config(), originals);
  }

  public static ConfigDef config() {
    return new ConfigDef()
            .define(SOURCE_TRACKING_URL, Type.STRING, "http://0.0.0.0:5000/", new EndpointValidator(), ConfigDef.Importance.HIGH, "mlflow tracking url")
            .define(TOPIC_CONFIG, ConfigDef.Type.STRING, "model-export", new ConfigDef.NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, "name of the topic to produce to");

  }
}
