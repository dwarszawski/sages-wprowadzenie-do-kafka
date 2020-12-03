#!/bin/bash

# create a topic with replication factor of 3
docker run \
  --net=host \
  --rm \
  confluentinc/cp-kafka:5.0.0 \
  kafka-topics --create --topic test --partitions 3 --replication-factor 3 --if-not-exists --zookeeper localhost:32181


# generate 10KB of random data

# start a continuous random producer
docker run \
  --net=host \
  --rm \
  confluentinc/cp-kafka:5.0.0 \
  base64 /dev/urandom | head -c 10000 | egrep -ao "\w" | tr -d '\n' > file10KB.txt  && kafka-producer-perf-test --topic test --num-records 10000 --throughput 10 --payload-file file10KB.txt --producer-props acks=1 bootstrap.servers=localhost:29092,localhost:39092,localhost:49092 --payload-delimiter A

# start a consumer
docker run \
 --net=host \
 --rm \
 confluentinc/cp-kafka:5.0.0 \
 kafka-console-consumer --bootstrap-server localhost:29092,localhost:39092,localhost:49092 --topic test

# trace logs from one of the kafka brokers
docker logs --follow kafka-3
# kill one kafka server - all should be fine
docker kill kafka-1
docker run \
    --net=host \
    --rm \
    confluentinc/cp-kafka:5.0.0 \
    kafka-topics --describe --topic test --zookeeper localhost:32181


# kill another kafka server
docker kill kafka-2
# describe the topic
docker run \
    --net=host \
    --rm \
    confluentinc/cp-kafka:5.0.0 \
    kafka-topics --describe --topic test --zookeeper localhost:32181

# kill the last server
docker kill kafka-3

# start back the servers one by one
docker start kafka-1
docker start kafka-2
docker start kafka-3

