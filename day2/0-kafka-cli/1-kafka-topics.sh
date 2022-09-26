# list topics
kafka-topics --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --list

# create first topic
kafka-topics --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --partitions 3 --replication-factor 3 --create --topic sages

# describe created topic
kafka-topics --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --describe --topic sages

# delete topic
kafka-topics --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --delete --topic sages