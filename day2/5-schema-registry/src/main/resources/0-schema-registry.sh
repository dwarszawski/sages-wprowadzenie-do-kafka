#!/usr/bin/env bash

## run on linux
docker run -d \
 -p 8081:8081 \
 --network mynetwork \
 --name=schema-registry \
 -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://172.17.0.1:29092 \
 -e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
 -e SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081 \
 -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_METHODS=GET,POST,OPTIONS,PUT \
 -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_ORIGIN=* \
 -e SCHEMA_REGISTRY_DEBUG=true \
 confluentinc/cp-schema-registry:6.2.1

## run on windows
docker run -d -p 8081:8081 --network mynetwork --name=schema-registry -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://172.17.0.1:29092 -e SCHEMA_REGISTRY_HOST_NAME=schema-registry -e SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081 -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_METHODS=GET,POST,OPTIONS,PUT -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_ORIGIN=* -e SCHEMA_REGISTRY_DEBUG=true confluentinc/cp-schema-registry:6.2.1

# http://localhost:8081/schemas/types/

# http://localhost:8081/schemas/subjects/

## run on linux
docker run -d \
  -p 8000:8000 \
  --network mynetwork \
  --name=schema-registry-ui \
  -e "SCHEMAREGISTRY_URL=http://172.17.0.1:8081" \
  landoop/schema-registry-ui

## run on windows
docker run -d -p 8000:8000 --network mynetwork --name=schema-registry-ui -e "SCHEMAREGISTRY_URL=http://172.17.0.1:8081" landoop/schema-registry-ui
## from browser open http://localhost:8000/#/

## http://172.17.0.1:8081/subjects

## http://172.17.0.1:8081/schemas/ids/1