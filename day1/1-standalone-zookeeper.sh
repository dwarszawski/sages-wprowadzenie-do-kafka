#!/bin/bash

docker run -d --net=host --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:latest

docker ps
netstat -nltp | grep 32181

# disable RAM Swap - can set to 0 on certain Linux distro
# sudo sysctl vm.swappiness=1
# echo 'vm.swappiness=1' | sudo tee --append /etc/sysctl.conf

# exec bash on container
docker exec -it zookeeper  /bin/bash

# zookeeper config
cat /etc/kafka/zookeeper.properties

# stop zookeeper and kill the container
zookeeper-server-stop

# check if listening on client port
nc -vz localhost 32181

# with whitelisting four letter commands
docker run -d --net=host --name=zookeeper2  -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000  -e ZOOKEEPER_SYNC_LIMIT=2  -e KAFKA_OPTS="-Dzookeeper.4lw.commands.whitelist=*"   confluentinc/cp-zookeeper:latest

# healthcheck with zookeeper command
echo "ruok" | nc localhost 32181 ; echo

# check the logs
cat /var/log/kafka/zookeeper-gc.log.0.current
docker logs zookeeper2


# REFERENCES
#https://docs.confluent.io/current/installation/docker/image-reference.html
#https://hub.docker.com/u/confluentinc/

#https://docs.confluent.io/current/installation/docker/config-reference.html
#https://hub.docker.com/r/confluentinc/cp-kafka

