# consume messages from topic
kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages

# consume messages from beginning of topic (if no offset is registered)
# order of messages is not total, order is per partiton
kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic streams-plaintext-input --from-beginning

# kafka consumer group - only one consumer from group will receive message
kafka-consumer-groups --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --list
kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages --from-beginning

# create consumer in specific group
kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages --group sages --from-beginning
# and run it again
kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages --group sages --from-beginning
kafka-consumer-groups --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --group sages --describe

# scaling consumers - automatic repartition - see consumer id for each partition
kafka-consumer-groups --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --group sages --describe

# cannot rest offsets while active consumers in the group
kafka-consumer-groups --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --group sages --reset-offsets --to-earliest --dry-run
# stop consumers - reset offset
kafka-consumer-groups --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --group sages --reset-offsets --to-earliest --execute
# start consumer from beginning
kafka-console-consumer --bootstrap-server kafka-1:29092,kafka-2:39092,kafka-3:49092 --topic sages --group sages --from-beginning