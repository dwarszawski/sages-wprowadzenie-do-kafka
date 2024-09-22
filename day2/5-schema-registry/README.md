
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
    confluentinc/cp-schema-registry:7.7.1
```


* running container on Windows

```shell 
docker run -d -p 8081:8081 --network mynetwork --name=schema-registry -e SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS=PLAINTEXT://kafka-1:29092 -e SCHEMA_REGISTRY_HOST_NAME=schema-registry -e SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081 -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_METHODS=GET,POST,OPTIONS,PUT -e SCHEMA_REGISTRY_ACCESS_CONTROL_ALLOW_ORIGIN=* -e SCHEMA_REGISTRY_DEBUG=true confluentinc/cp-schema-registry:7.7.1
```

* REST API available

    http://localhost:8081/schemas/types/

    http://localhost:8081/schemas/subjects/


### Connect to Schema Registry using Kafka UI

* Go to `locahost:8080`
* From `Dashboard` tab select 'configure' on existing `sages` cluster
* Select `Configure Schema Registry`
* Set `URL` to `http://schema-registry:8081`
* Click `Validate`
* Click `Submit`

### Create new schema version using Kafka UI

* Go to `locahost:8080`
* Expand `sages` cluster menu
* Select `Schema Registry`
* Click `Create Schema` with Subject `test-value` and Schema Type `Avro` and Schema as json below

```json
{
  "type": "record",
  "namespace": "com.sages.schema.evolution.backward",
  "name": "TransactionV1",
  "fields": [
    {
      "name": "transactionId",
      "type": "long"
    },
    {
      "name": "transactionValue",
      "type": "double"
    }
  ]
}

```