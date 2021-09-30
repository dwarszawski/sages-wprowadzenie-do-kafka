#!/usr/bin/env bash

docker run -d \
  -p 8081:8081 \
  --network mynetwork \
  --name=schema-registry \
  -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://172.17.0.1:9092 \
  -e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
  -e SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081 \
  -e SCHEMA_REGISTRY_DEBUG=true \
  confluentinc/cp-schema-registry:6.2.1



# http://localhost:8081/schemas/types/

# http://localhost:8081/schemas/subjects/


docker run --rm \
  -p 8080:8000 \
  --network mynetwork \
  --name=schema-registry-ui \
  -e "SCHEMAREGISTRY_URL=http://172.17.0.1:8081" \
  landoop/schema-registry-ui


## http://172.17.0.1:8081/subjects

## http://172.17.0.1:8081/schemas/ids/1