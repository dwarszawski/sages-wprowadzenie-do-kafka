# produce messages to specified topic
kafka-console-producer --broker-list localhost:9092 --topic streams-plaintext-input

# pass user defined properties
kafka-console-producer --broker-list localhost:9092 --topic first-topic --producer-property acks=all

# create a topic if not exists
kafka-console-producer --broker-list localhost:9092 --topic new-topic

# recommendation to create topic manually before using them because of the defaults values e.g. num of partitions