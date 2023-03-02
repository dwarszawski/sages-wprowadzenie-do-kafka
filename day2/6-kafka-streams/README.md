## REQUIRED FOR SINGLE KAFKA BROKER

```bash

docker network create mynetwork

# run standalone zookeeper
docker run -d -p 32181:32181 --network mynetwork --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:7.3.2

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
            confluentinc/cp-kafka:7.3.2

# create topics scripts

kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transactions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transaction_descriptions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic enriched_transactions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic balances
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transactions_timestamp 

kafka-console-consumer --bootstrap-server localhost:29092 --topic balances --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.DoubleDeserializer"
kafka-console-consumer --bootstrap-server localhost:29092 --topic transaction_processor-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.DoubleDeserializer"
# shuffling - mapping from new key to old key
# This redistribution stage, usually called data shuffling, 
# ensures that data is organized in partitions that can be processed in parallel. 
# The reshuffled streams are stored and piped via specific Kafka topics called repartition topics.
# By using Kafka topics to persist reshuffled streams instead of relying on interprocess communication directly,
# Kafka Streams effectively separates a single processor topology into smaller sub-topologies, 
# connected by those repartition topics
# (each repartition topic is both a sink topic of the upstream sub-topology and a source topic of the downstream sub-topology).
# Sub-topologies can then be executed as independent stream tasks through parallel threads.
kafka-console-consumer --bootstrap-server localhost:29092 --topic transaction_processor1-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer" --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"
kafka-console-consumer --bootstrap-server localhost:29092 --topic transactions_timestamp --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.StringDeserializer"
kafka-console-consumer --bootstrap-server localhost:29092 --topic suspicious_accounts --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.StringDeserializer"



kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic suspicious_accounts

 key       window start  window end       value
[708877241@1624914000000/1624917600000], -2000.0


kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transactions_hop_groups

```