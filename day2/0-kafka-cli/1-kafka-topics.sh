# list topics
kafka-topics --bootstrap-server localhost:9092 --list

# create first topic
kafka-topics --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1 --create --topic first-topic

# describe created topic
kafka-topics --bootstrap-server localhost:9092 --describe --topic first-topic

# delete topic
kafka-topics --bootstrap-server localhost:9092 --delete --topic first-topic