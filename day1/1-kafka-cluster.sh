#!/bin/bash

#####################################################################################################################################################################################

# Start Zookeeper cluster

## Linux version
docker run -d \
   -p 22181:22181 \
   -p 23888:23888 \
   --name=zzk-1 \
   --network mynetwork \
   -e ZOOKEEPER_SERVER_ID=1 \
   -e ZOOKEEPER_CLIENT_PORT=22181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" \
   confluentinc/cp-zookeeper:latest

docker run -d \
  -p 32181:32181 \
  -p 33888:33888 \
  --name=zzk-2 \
  --network mynetwork \
  -e ZOOKEEPER_SERVER_ID=2 \
  -e ZOOKEEPER_CLIENT_PORT=32181 \
  -e ZOOKEEPER_TICK_TIME=2000 \
  -e ZOOKEEPER_INIT_LIMIT=5 \
  -e ZOOKEEPER_SYNC_LIMIT=2 \
  -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" \
  confluentinc/cp-zookeeper:latest

docker run -d \
   -p 42181:42181 \
   -p 43888:43888 \
   --name=zzk-3 \
   --network mynetwork \
   -e ZOOKEEPER_SERVER_ID=3 \
   -e ZOOKEEPER_CLIENT_PORT=42181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" \
   confluentinc/cp-zookeeper:latest

## Windows version
docker run -d -p 22181:22181 -p 23888:23888 --name=zzk-1 --network mynetwork -e ZOOKEEPER_SERVER_ID=1 -e ZOOKEEPER_CLIENT_PORT=22181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_INIT_LIMIT=5 -e ZOOKEEPER_SYNC_LIMIT=2 -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" confluentinc/cp-zookeeper:latest
docker run -d -p 32181:32181 -p 33888:33888 --name=zzk-2 --network mynetwork -e ZOOKEEPER_SERVER_ID=2 -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_INIT_LIMIT=5 -e ZOOKEEPER_SYNC_LIMIT=2 -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" confluentinc/cp-zookeeper:latest
docker run -d -p 42181:42181 -p 43888:43888 --name=zzk-3 --network mynetwork -e ZOOKEEPER_SERVER_ID=3 -e ZOOKEEPER_CLIENT_PORT=42181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_INIT_LIMIT=5 -e ZOOKEEPER_SYNC_LIMIT=2 -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" confluentinc/cp-zookeeper:latest

# https://docs.cloudera.com/HDPDocuments/HDP3/HDP-3.1.5/administration/content/zookeeper-ports.html

#####################################################################################################################################################################################

# Check logs
docker ps
docker logs zzk-1
docker logs zzk-2
docker logs zzk-3

#####################################################################################################################################################################################

# Run kafka cluster on linux
docker run -d \
    --name=kafka-1 \
    -p 29092:29092 \
    --network mynetwork \
    -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-1:29092 \
    -e KAFKA_MIN_INSYNC_REPLICAS=2 \
    confluentinc/cp-kafka:latest

docker run -d \
    --name=kafka-2 \
    -p 39092:39092 \
    --network mynetwork \
    -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-2:39092 \
    -e KAFKA_MIN_INSYNC_REPLICAS=2 \
    confluentinc/cp-kafka:latest


 docker run -d \
     --name=kafka-3 \
    -p 49092:49092 \
     --network mynetwork \
     -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 \
     -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-3:49092 \
     -e KAFKA_MIN_INSYNC_REPLICAS=2 \
     confluentinc/cp-kafka:latest

# Run kafka cluster on windows
docker run -d --name=kafka-1 -p 29092:29092 --network mynetwork -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-1:29092 -e KAFKA_MIN_INSYNC_REPLICAS=2 confluentinc/cp-kafka:latest
docker run -d --name=kafka-2 -p 39092:39092 --network mynetwork -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-2:39092 -e KAFKA_MIN_INSYNC_REPLICAS=2 confluentinc/cp-kafka:latest
docker run -d --name=kafka-3 -p 49092:49092 --network mynetwork -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-3:49092 -e KAFKA_MIN_INSYNC_REPLICAS=2 confluentinc/cp-kafka:latest

#####################################################################################################################################################################################

# Check kafka logs
docker ps
docker logs kafka-1 | grep started
docker logs kafka-2 | grep started
docker logs kafka-3 | grep started


#####################################################################################################################################################################################

# Create a first topic
## Linux version
docker run \
  --rm \
  --network mynetwork \
  confluentinc/cp-kafka:latest \
  kafka-topics --create --topic test --partitions 3 --replication-factor 3 --if-not-exists --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092

## Windows version
docker run --rm --network mynetwork confluentinc/cp-kafka:latest kafka-topics --create --topic test --partitions 3 --replication-factor 3 --if-not-exists --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092

# Show the topic description
## Linux version
docker run \
    --network mynetwork \
    --rm \
    confluentinc/cp-kafka:latest \
    kafka-topics --describe --topic test --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092
## Windows version
docker run --network mynetwork --rm confluentinc/cp-kafka:latest kafka-topics --describe --topic test --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092

#####################################################################################################################################################################################

# REFERENCES
# https://docs.confluent.io/latest/installation/docker/docs/installation/clustered-deployment.html
