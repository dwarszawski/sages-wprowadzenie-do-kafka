kafka-topics --bootstrap-server http://172.17.0.1:29092, http://172.17.0.1:39092, http://172.17.0.1:49092 --partitions 3 --replication-factor 3 --create --topic transactions
kafka-topics --bootstrap-server http://172.17.0.1:29092, http://172.17.0.1:39092, http://172.17.0.1:49092 --partitions 3 --replication-factor 3 --create --topic avro.transactions
