#!/bin/bash

# Add file limits configs - allow to open 100,000 file descriptors
#echo "* hard nofile 100000
#* soft nofile 100000" | sudo tee --append /etc/security/limits.conf

docker network create totalizator

# start ZK
docker run -d \
   -p 22181:22181 \
   -p 23888:23888 \
   --name=zzk-1 \
   --network totalizator \
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
   --network totalizator \
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
   --network totalizator \
   -e ZOOKEEPER_SERVER_ID=3 \
   -e ZOOKEEPER_CLIENT_PORT=42181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" \
   confluentinc/cp-zookeeper:latest

# sprawdź logi kontenerów
docker ps
docker logs zzk-1
docker logs zzk-2
docker logs zzk-3

# uruchom klaster kafka
docker run -d \
    --name=kafka-1 \
    -p 29092:29092 \
    --network totalizator \
    -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-1:29092 \
    -e KAFKA_MIN_INSYNC_REPLICAS=2 \
    confluentinc/cp-kafka:5.0.0

docker run -d \
    --name=kafka-2 \
    -p 39092:39092 \
    --network totalizator \
    -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-2:39092 \
    -e KAFKA_MIN_INSYNC_REPLICAS=2 \
    confluentinc/cp-kafka:5.0.0

 docker run -d \
     --name=kafka-3 \
    -p 49092:49092 \
     --network totalizator \
     -e KAFKA_ZOOKEEPER_CONNECT=zzk-1:22181,zzk-2:32181,zzk-3:42181 \
     -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka-3:49092 \
     -e KAFKA_MIN_INSYNC_REPLICAS=2 \
     confluentinc/cp-kafka:5.0.0

# sprawdź logi kontenerów
docker ps
docker logs kafka-1 | grep started
docker logs kafka-2 | grep started
docker logs kafka-3 | grep started

# utwórz pierwszy "topic"
docker run \
  --rm \
  --network totalizator \
  confluentinc/cp-kafka:5.0.0 \
  kafka-topics --create --topic foobar --partitions 3 --replication-factor 3 --if-not-exists --zookeeper zzk-2:32181

# wyświetl szczegóły utworzonego "topicu"
docker run \
    --network totalizator \
    --rm \
    confluentinc/cp-kafka:5.0.0 \
    kafka-topics --describe --topic foobar --zookeeper zzk-2:32181


# wyślij wiadomości na kolejkę
docker run \
  --network totalizator \
  --rm confluentinc/cp-kafka:5.0.0 \
  bash -c "seq 42 | kafka-console-producer --broker-list kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic foobar && echo 'Produced 42 messages.'"

# uruchom konsumenta dla kolejki
docker run \
  --network totalizator \
 --rm \
 confluentinc/cp-kafka:5.0.0 \
 kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic foobar --from-beginning --max-messages 42

# TODO: check the zookeeper navigator

# REFERENCES
# https://docs.confluent.io/5.0.0/installation/docker/docs/installation/clustered-deployment.html
