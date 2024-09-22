## REQUIRED FOR SINGLE KAFKA BROKER

```bash
# create topics scripts

kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transactions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transaction_descriptions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic balances
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic suspicious_accounts

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
kafka-console-consumer --bootstrap-server localhost:29092 --topic suspicious_accounts --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.StringDeserializer"




 key       window start  window end       value
[708877241@1624914000000/1624917600000], -2000.0

```