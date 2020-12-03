# consume messages from topic
kafka-console-consumer --bootstrap-server localhost:9092 --topic first-topic

# consume messages from beginning of topic
# order of messages is not total, order is per partiton
kafka-console-consumer --bootstrap-server localhost:9092 --topic streams-plaintext-input --from-beginning

# kafka consumer group - only one consumer from group will receive message
kafka-consumer-groups --bootstrap-server localhost:9092 --list
kafka-console-consumer --bootstrap-server localhost:9092 --topic first-topic --from-beginning

# create consumer in specific group
kafka-console-consumer --bootstrap-server localhost:9092 --topic first-topic --group first-group --from-beginning
# and run it again
kafka-console-consumer --bootstrap-server localhost:9092 --topic first-topic --group first-group --from-beginning
kafka-consumer-groups --bootstrap-server localhost:9092 --group first-group --describe

# scaling consumers - automatic repartition - see consumer id for each partition
kafka-consumer-groups --bootstrap-server localhost:9092 --group first-group --describe

# cannot rest offsets while active consumers in the group
kafka-consumer-groups --bootstrap-server localhost:9092 --group first-group3 --reset-offsets --to-earliest --dry-run
# stop consumers - reset offset
kafka-consumer-groups --bootstrap-server localhost:9092 --group first-group3 --reset-offsets --to-earliest --execute
# start consumer from beginning
kafka-console-consumer --bootstrap-server localhost:9092 --topic first-topic --group first-group --from-beginning