## REQUIRED FOR SINGLE KAFKA BROKER

```bash
# create topics scripts

kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transactions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic transaction_descriptions
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic balances
kafka-topics --bootstrap-server kafka-1:29092 --partitions 3 --replication-factor 3 --create --topic suspicious_accounts

kafka-console-consumer --bootstrap-server localhost:29092 --topic balances --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.DoubleDeserializer"
kafka-console-consumer --bootstrap-server localhost:29092 --topic transaction_processor-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.DoubleDeserializer"
kafka-console-consumer --bootstrap-server localhost:29092 --topic transaction_processor1-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer" --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"
kafka-console-consumer --bootstrap-server localhost:29092 --topic suspicious_accounts --property print.key=true  --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.LongDeserializer"  --value-deserializer "org.apache.kafka.common.serialization.StringDeserializer"

 key       window start  window end       value
[708877241@1624914000000/1624917600000], -2000.0

```