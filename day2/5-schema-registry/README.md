
### Setting up Schema Registry container

* running container on Linux

```shell 
    docker run -d \
    -p 8081:8081 \
    --network mynetwork \
    --name=schema-registry \
    -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://kafka-1:29092 \
    -e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
    -e SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081 \
    -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_METHODS=GET,POST,OPTIONS,PUT \
    -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_ORIGIN=* \
    -e SCHEMA_REGISTRY_DEBUG=true \
    confluentinc/cp-schema-registry:7.3.2
```


* running container on Windows

```shell 
docker run -d -p 8081:8081 --network mynetwork --name=schema-registry -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://kafka-1:29092 -e SCHEMA_REGISTRY_HOST_NAME=schema-registry -e SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081 -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_METHODS=GET,POST,OPTIONS,PUT -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_ORIGIN=* -e SCHEMA_REGISTRY_DEBUG=true confluentinc/cp-schema-registry:7.3.2
```

* REST API available

    http://localhost:8081/schemas/types/

    http://localhost:8081/schemas/subjects/


### Setting up Schema Registry UI container

* running container on Linux

```shell 
    docker run -d \
    -p 8084:8000 \
    --network mynetwork \
    --name=schema-registry-ui \
    -e "SCHEMAREGISTRY_URL=http://localhost:8081" \
    landoop/schema-registry-ui
```

* running container on Windows

```shell 
    docker run -d -p 8084:8000 --network mynetwork --name=schema-registry-ui -e "SCHEMAREGISTRY_URL=http://localhost:8081" landoop/schema-registry-ui
```

### Create new schema version using Schema Registry UI

* Create new schema/update existing schema. Schema Registry UI is available [here](http://{gateway-address}:8084/#/)

* REST API

    http://{gateway-address}:8084/subjects

    http://{gateway-address}:8084/schemas/ids/1