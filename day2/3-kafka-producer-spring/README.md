kafka-topics --bootstrap-server http://kafka-1:29092, http://kafka-2:39092, http://kafka-3:49092 --partitions 3 --replication-factor 3 --create --topic transactions
kafka-topics --bootstrap-server http://kafka-1:29092, http://kafka-2:39092, http://kafka-3:49092 --partitions 3 --replication-factor 3 --create --topic avro.transactions
