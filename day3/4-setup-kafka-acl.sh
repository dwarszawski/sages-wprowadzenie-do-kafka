#!/usr/bin/env bash

# clean stopped containers
docker rm $(docker ps -q -f status=exited)

# run zookeeper
docker run -d --net=host --name=zookeeper \
-e ZOOKEEPER_CLIENT_PORT=32181 \
-e ZOOKEEPER_TICK_TIME=2000 \
-e ZOOKEEPER_SYNC_LIMIT=2 \
confluentinc/cp-zookeeper:latest

# configure Kafka Broker
docker build -f ../3-1-setup-server-dockerfile -t kafka-ssl-auth ../
docker run -d --name=ssl-kafka-1 \
              --net=host -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL \
              -e KAFKA_SSL_CLIENT_AUTH=required \
              -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 \
              -e KAFKA_ADVERTISED_LISTENERS=SSL://localhost.example.com:19093 \
              -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks \
              -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds \
              -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds \
              -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks \
              -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds \
              -e KAFKA_AUTHORIZER_CLASS_NAME=kafka.security.authorizer.AclAuthorizer \
              -e "KAFKA_SUPER_USERS=User:CN=localhost.example.com" \
              -e KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=false \
              -e KAFKA_LOG4J_LOGGERS="kafka.authorizer.logger=DEBUG" \
              kafka-ssl-auth

docker run -d --name=ssl-kafka-2 \
              --net=host \
              -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL \
              -e KAFKA_SSL_CLIENT_AUTH=required \
              -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 \
              -e KAFKA_ADVERTISED_LISTENERS=SSL://localhost.example.com:29093 \
              -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks \
              -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds \
              -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds \
              -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks \
              -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds \
              -e KAFKA_AUTHORIZER_CLASS_NAME=kafka.security.authorizer.AclAuthorizer \
              -e "KAFKA_SUPER_USERS=User:CN=localhost.example.com" \
              -e KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=false \
              -e KAFKA_LOG4J_LOGGERS="kafka.authorizer.logger=DEBUG" \
              kafka-ssl-auth

docker run -d --name=ssl-kafka-3 \
              --net=host \
              -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL \
              -e KAFKA_SSL_CLIENT_AUTH=required \
              -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 \
              -e KAFKA_ADVERTISED_LISTENERS=SSL://localhost.example.com:39093 \
              -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks \
              -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds \
              -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds \
              -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks \
              -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds \
              -e KAFKA_AUTHORIZER_CLASS_NAME=kafka.security.authorizer.AclAuthorizer \
              -e "KAFKA_SUPER_USERS=User:CN=localhost.example.com" \
              -e KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=false \
              -e KAFKA_LOG4J_LOGGERS="kafka.authorizer.logger=DEBUG" \
              kafka-ssl-auth

docker ps

docker run  --net=host \
  kafka-ssl-client  \
  kafka-topics --zookeeper localhost.example.com:32181 \
               --create \
               --topic acl-test \
               --replication-factor 2 --partitions 2


# Allow client to consume to topic acl-test - no ssl/acl required - ZK is not secured
docker run --net=host kafka-ssl-client kafka-acls --authorizer-properties zookeeper.connect=localhost.example.com:32181 --add --allow-principal User:CN=dwarszawski-XPS-15-9570 --consumer --topic acl-test --group test-group

# Allow client to produce to topic acl-test - no ssl/acl required - ZK is not secured
docker run --net=host kafka-ssl-client kafka-acls --authorizer-properties zookeeper.connect=localhost.example.com:32181 --add --allow-principal User:CN=dwarszawski-XPS-15-9570 --producer --topic acl-test

# Listing acls
docker run --net=host \
  kafka-ssl-client  \
  kafka-acls \
    --authorizer-properties zookeeper.connect=localhost.example.com:32181 \
    --list \
    --topic acl-test


# try to produce message
# run container in new terminal
docker run -it --net=host  kafka-ssl-client /bin/bash

# within container
kafka-console-producer --broker-list localhost.example.com:19093,localhost.example.com:29093,localhost.example.com:39093 --topic acl-test --producer.config /tmp/client-ssl.properties

# REFERENCES
# https://docs.confluent.io/current/kafka/authorization.html
