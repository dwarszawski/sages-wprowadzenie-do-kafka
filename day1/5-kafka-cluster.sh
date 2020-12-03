#!/bin/bash

# Add file limits configs - allow to open 100,000 file descriptors
#echo "* hard nofile 100000
#* soft nofile 100000" | sudo tee --append /etc/security/limits.conf

# start ZK
docker run -d \
   --net=host \
   --name=zzk-1 \
   -e ZOOKEEPER_SERVER_ID=1 \
   -e ZOOKEEPER_CLIENT_PORT=22181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="localhost:22888:23888;localhost:32888:33888;localhost:42888:43888" \
   confluentinc/cp-zookeeper:latest

docker run -d \
   --net=host \
   --name=zzk-2 \
   -e ZOOKEEPER_SERVER_ID=2 \
   -e ZOOKEEPER_CLIENT_PORT=32181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="localhost:22888:23888;localhost:32888:33888;localhost:42888:43888" \
   confluentinc/cp-zookeeper:latest

docker run -d \
   --net=host \
   --name=zzk-3 \
   -e ZOOKEEPER_SERVER_ID=3 \
   -e ZOOKEEPER_CLIENT_PORT=42181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="localhost:22888:23888;localhost:32888:33888;localhost:42888:43888" \
   confluentinc/cp-zookeeper:latest

# sprawdź logi kontenerów
docker ps
docker logs zzk-1
docker logs zzk-2
docker logs zzk-3

# uruchom klaster kafka
docker run -d \
    --net=host \
    --name=kafka-1 \
    -e KAFKA_ZOOKEEPER_CONNECT=localhost:22181,localhost:32181,localhost:42181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:29092 \
    -e KAFKA_MIN_INSYNC_REPLICAS=2 \
    confluentinc/cp-kafka:5.0.0

docker run -d \
    --net=host \
    --name=kafka-2 \
    -e KAFKA_ZOOKEEPER_CONNECT=localhost:22181,localhost:32181,localhost:42181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:39092 \
    -e KAFKA_MIN_INSYNC_REPLICAS=2 \
    confluentinc/cp-kafka:5.0.0

 docker run -d \
     --net=host \
     --name=kafka-3 \
     -e KAFKA_ZOOKEEPER_CONNECT=localhost:22181,localhost:32181,localhost:42181 \
     -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:49092 \
     -e KAFKA_MIN_INSYNC_REPLICAS=2 \
     confluentinc/cp-kafka:5.0.0

# sprawdź logi kontenerów
docker ps
docker logs kafka-1 | grep started
docker logs kafka-2 | grep started
docker logs kafka-3 | grep started

# utwórz pierwszy "topic"
docker run \
  --net=host \
  --rm \
  confluentinc/cp-kafka:5.0.0 \
  kafka-topics --create --topic hello --partitions 3 --replication-factor 3 --if-not-exists --zookeeper localhost:32181

# wyświetl szczegóły utworzonego "topicu"
docker run \
    --net=host \
    --rm \
    confluentinc/cp-kafka:5.0.0 \
    kafka-topics --describe --topic hello --zookeeper localhost:32181


# wyślij wiadomości na kolejkę
docker run \
  --net=host \
  --rm confluentinc/cp-kafka:5.0.0 \
  bash -c "seq 42 | kafka-console-producer --broker-list localhost:29092,localhost:39092,localhost:49092 --topic hello && echo 'Produced 42 messages.'"

# uruchom konsumenta dla kolejki
docker run \
 --net=host \
 --rm \
 confluentinc/cp-kafka:5.0.0 \
 kafka-console-consumer --bootstrap-server localhost:29092,localhost:39092,localhost:49092 --topic hello --from-beginning --max-messages 42

# TODO: check the zookeeper navigator

# REFERENCES
# https://docs.confluent.io/5.0.0/installation/docker/docs/installation/clustered-deployment.html
