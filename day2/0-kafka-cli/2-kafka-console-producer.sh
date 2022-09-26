# produce messages to specified topic
kafka-console-producer --broker-list kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic streams-plaintext-input

# pass user defined properties
kafka-console-producer --broker-list kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages --producer-property acks=all

# create a topic if not exists
kafka-console-producer --broker-list kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages

# recommendation to create topic manually before using them because of the defaults values e.g. num of partitions