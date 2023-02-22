#!/bin/bash

#####################################################################################################################################################################################

# Create a topic

## Linux version
docker run \
  --network mynetwork \
  --rm \
  confluentinc/cp-kafka:latest \
  kafka-topics --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --create --topic mytopic --partitions 3 --replication-factor 3 --if-not-exists

## Windows version
docker run --network mynetwork --rm confluentinc/cp-kafka:latest kafka-topics --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --create --topic mytopic --partitions 3 --replication-factor 3


#####################################################################################################################################################################################

# Start short living producer

## Linux version
docker run \
  -it \
  --network mynetwork \
  --rm \
  confluentinc/cp-kafka:latest \
 /bin/bash

## Windows version
docker run  -it  --network mynetwork  --rm  confluentinc/cp-kafka:latest /bin/bash

#base64 /dev/urandom | head -c 10000 | egrep -ao "\w" | tr -d '\n' > random.txt  && kafka-producer-perf-test --topic test --num-records 10000 --throughput 5 --payload-file random.txt --producer-props acks=1 bootstrap.servers=kafka-1:29092,kafka-2:39092,kafka-3:49092 --payload-delimiter A

#####################################################################################################################################################################################

# Start a short living consumer

## Linux version
docker run \
 --rm \
 --network mynetwork \
 confluentinc/cp-kafka:latest \
 kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic mytopic

## Windows version
docker run --rm --network mynetwork confluentinc/cp-kafka:latest kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic mytopic

#####################################################################################################################################################################################

# Trace logs from one of the kafka brokers
docker logs --follow kafka-3
# Kill one of the kafka server
docker kill kafka-3

# Check the topic description
kafka-topics --describe --topic mytopic --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092

# Kill another kafka server
docker kill kafka-2

# Check the topic description
kafka-topics --describe --topic test --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092

# Start back the servers one by one
docker start kafka-2
docker start kafka-3

#####################################################################################################################################################################################