#!/bin/bash

# Add file limits configs - allow to open 100,000 file descriptors
# echo "* hard nofile 100000
# * soft nofile 100000" | sudo tee --append /etc/security/limits.conf

# run standalone zookeeper
docker run -d \
--net=host \
--name=zookeeper \
-e ZOOKEEPER_CLIENT_PORT=32181 \
-e ZOOKEEPER_TICK_TIME=2000 \
-e ZOOKEEPER_SYNC_LIMIT=2 \
confluentinc/cp-zookeeper:5.5.0

# check if zookeeper container is running
docker ps | grep zookeeper

# run single-node kafka broker
docker run -d \
    --net=host \
    --name=kafka \
    -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:29092 \
    -e KAFKA_BROKER_ID=test-1 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    confluentinc/cp-kafka:5.5.0

docker ps | grep kafka

# verify in zookeeper navigator
/broker/ids/1

# verify it's working
nc -vz localhost 29092

# look at the server logs
docker logs kafka


# from kafka container
docker exec -it kafka /bin/bash

# list available topics
kafka-topics --bootstrap-server localhost:29092 --list
# create new topic with given replicas and partitions
kafka-topics --bootstrap-server localhost:29092 --create --topic first_topic --replication-factor 1 --partitions 3
# verify if new topic is created
kafka-topics --bootstrap-server localhost:29092 --list
# describe new topic
kafka-topics --bootstrap-server localhost:29092 --topic first_topic --describe

# produce data to the topic
kafka-console-producer --bootstrap-server localhost:29092 --topic first_topic
abc
cba
(exit)
# read that data
kafka-console-consumer --bootstrap-server localhost:29092 --topic first_topic --from-beginning
