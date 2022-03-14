# Kafka Source Connector for Schema Registry
> Dedicated Kafka Connector to track changes in `Confluent Schema Registry`

## Table of contents
* [General info](#general-info)
* [Local setup](#local-setup)

## General info
Kafka Connector to track schema changes in given `Confluent Schema Registry` instance.

## Local setup
Connector is distributed as a `jar` file. In order to build the assembly use `mvn clean install -f ./4-kafka-connect-schema-registry`.
Assembly will be available in directory `./kafka-connect-schema-registry/target/kafka-connect-schema-registry-${project_version}-assembly.jar`.
Generated `jar` is mounted as the volume in `kafka-connect` container taken directly from `./target` directory.

Kafka Connect requires Apache Kafka and Apache Zookeeper servers.

### Setting up Kafka Connect container

* running container on Linux

```shell
    
    docker run -d \
    --name=kafka-connect \
    -p 8083:8083 \
    --network mynetwork \
    -e CONNECT_BOOTSTRAP_SERVERS=172.17.0.1:29092 \
    -e CONNECT_REST_PORT=8083 \
    -e CONNECT_GROUP_ID="connectors" \
    -e CONNECT_CONFIG_STORAGE_TOPIC="connectors-config" \
    -e CONNECT_OFFSET_STORAGE_TOPIC="connectors-offsets" \
    -e CONNECT_STATUS_STORAGE_TOPIC="connectors-status" \
    -e CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR=3 \
    -e CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR=3 \
    -e CONNECT_STATUS_STORAGE_REPLICATION_FACTOR=3 \
    -e CONNECT_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
    -e CONNECT_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
    -e CONNECT_INTERNAL_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
    -e CONNECT_INTERNAL_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
    -e CONNECT_REST_ADVERTISED_HOST_NAME="kafka-connect" \
    -e CONNECT_PLUGIN_PATH=/usr/share/java,/etc/kafka-connect/jars \
    -v /home/dwarszawski/Workspace/personal/sages/kafka-kurs/day2/4-kafka-connect-schema-registry/target:/etc/kafka-connect/jars \
    confluentinc/cp-kafka-connect:6.1.4
    #-v ${PWD}/target:/etc/kafka-connect/jars \
    #-v ${pwd}/target:/etc/kafka-connect/jars  for windows
    
```
* running container on Windows
```shell

    docker run -d --name=kafka-connect  -p 8083:8083 --network mynetwork -e CONNECT_BOOTSTRAP_SERVERS=172.17.0.1:29092 -e CONNECT_REST_PORT=8083 -e CONNECT_GROUP_ID="connectors" -e CONNECT_CONFIG_STORAGE_TOPIC="connectors-config" -e CONNECT_OFFSET_STORAGE_TOPIC="connectors-offsets" -e CONNECT_STATUS_STORAGE_TOPIC="connectors-status" -e CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR=3 -e CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR=3 -e CONNECT_STATUS_STORAGE_REPLICATION_FACTOR=3 -e CONNECT_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_INTERNAL_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_INTERNAL_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_REST_ADVERTISED_HOST_NAME="kafka-connect" -e CONNECT_PLUGIN_PATH=/usr/share/java,/etc/kafka-connect/jars -v /home/dwarszawski/Workspace/personal/sages/kafka-kurs/day2/4-kafka-connect-schema-registry/target:/etc/kafka-connect/jars confluentinc/cp-kafka-connect:6.1.4

```


### Setting up Kafka Connect UI container on Linux
```shell 
    docker run -d \
    --name=kafka-connect-ui \
    -p 8000:8000 \
    --network mynetwork \
    -e CONNECT_URL="http://172.17.0.1:8083" \
    landoop/kafka-connect-ui
```

### Setting up Kafka Connect UI container on Windows

```shell 

docker run -d --name=kafka-connect-ui  -p 8000:8000 --network mynetwork -e CONNECT_URL="http://172.17.0.1:8083" landoop/kafka-connect-ui

```

### Create output topic 

```shell

docker exec -it kafka-1 /bin/bash
kafka-topics --bootstrap-server 172.17.0.1:29092,172.17.0.1:39092,172.17.0.1:49092 --partitions 3 --replication-factor 3 --create --topic schema.updates

```

### Create instance of connect using Kafka Connect UI

Kafka Connect UI is available [here](http://172.17.0.1:8000/)

```text
    name=SchemaRegistrySourceConnector
    connector.class=com.dwarszawski.connector.SchemaRegistrySourceConnector
    tasks.max=1
    topic=schema.updates
    schema_registry_url=http://172.17.0.1:8081
```

Check logs of schema registry

```text
docker logs -f schema-registry
```

### Create new schema version using Schema Registry UI

* Run console consumer consumer from on of kafka containers

```shell 
    kafka-console-consumer --bootstrap-server localhost:29092 --topic schema.updates
```

* Create new schema/update existing schema. Schema Registry UI is available [here](http://localhost:8084/#/)
