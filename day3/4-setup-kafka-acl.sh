#!/usr/bin/env bash

# clean stopped containers
docker rm $(docker ps -q -f status=exited)

* Run Zookeeper container

```text
docker run -d --network mynetwork --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:latest
```

* Run Kafka brokers with SSL authentication enabled

```text
docker run -d --network mynetwork --name=kafkabroker --hostname=kafkabroker -p 9093:9093 -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL  -e KAFKA_AUTHORIZER_CLASS_NAME=kafka.security.authorizer.AclAuthorizer -e "KAFKA_SUPER_USERS=User:CN=kafkabroker" -e KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=false -e KAFKA_LOG4J_LOGGERS="kafka.authorizer.logger=DEBUG" -e KAFKA_SSL_CLIENT_AUTH=required -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafkabroker:9092,SSL://kafkabroker:9093 -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 kafka-ssl-encrypted
```

* Verify ACL authorization with Kafka producer

```text

docker run -it  --net=mynetwork  kafka-ssl-client /bin/bash

# should fail on authorization
kafka-topics --bootstrap-server  kafkabroker:9093  --create --replication-factor 1 --partitions 1 --topic sensitive-data  --command-config /tmp/client-ssl.properties
```

* Create topic with super user
```text
docker run -it  --net=mynetwork  kafka-ssl-client /bin/bash

# ability to create topic through zookeeper - no acl configured on Zookeeper
kafka-topics --zookeeper  zookeeper:32181  --create --replication-factor 1 --partitions 1 --topic sensitive-data  --command-config /tmp/client-ssl.properties

# ability to create topic as a Super User
docker exec -it kafkabroker /bin/bash
kafka-topics --bootstrap-server  kafkabroker:9093  --create --replication-factor 1 --partitions 1 --topic sensitive-data  --command-config /tmp/client-ssl.properties

```

* Adding ACLs as a Super User

```text
docker exec -it kafkabroker /bin/bash

# List current ACLs
kafka-acls --bootstrap-server kafkabroker:9093   --list --topic sensitive-data --command-config /tmp/client-ssl.properties

# Assign READ permissions to `kafkaclient`
kafka-acls --bootstrap-server kafkabroker:9093  --add --allow-principal User:CN=kafkaclient --consumer --topic sensitive-data --group kafkaclients --command-config /tmp/client-ssl.properties

# Assign WRITE permissions to `kafkaclient`
kafka-acls --bootstrap-server kafkabroker:9093 --add --allow-principal User:CN=kafkaclient --producer --topic sensitive-data --command-config /tmp/client-ssl.properties

# List created ACLs
kafka-acls --bootstrap-server kafkabroker:9093   --list --topic sensitive-data --command-config /tmp/client-ssl.properties
```


* Verify ACL authorization with Kafka producer

```text

docker run -it  --net=mynetwork  kafka-ssl-client /bin/bash
kafka-console-producer --broker-list kafkabroker:9093 --topic sensitive-data  --producer.config /tmp/client-ssl.properties

```

* Verify ACL authorization with Kafka consumer

```text
docker run -it --net=mynetwork   kafka-ssl-client /bin/bash
kafka-console-consumer --bootstrap-server kafkabroker:9093 --topic sensitive-data  --group kafkaclients --consumer.config /tmp/client-ssl.properties

```
