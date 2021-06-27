## REQUIRED FOR SINGLE KAFKA BROKER

```bash

docker network create mynetwork

# run standalone zookeeper
docker run -d -p 32181:32181 --network mynetwork --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:latest

# check if zookeeper container is running
docker ps | grep zookeeper

# run single-node kafka broker
        docker run -d \
            --network mynetwork \
            --name=kafka \
            -p 9092:9092 \
            -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:32181 \
            -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.17.0.1:9092 \
            -e KAFKA_BROKER_ID=1 \
            -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
            -e  KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 \
            -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 \
            -e KAFKA_MIN_INSYNC_REPLICAS=1 \
            confluentinc/cp-kafka:latest

```