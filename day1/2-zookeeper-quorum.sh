#!/bin/bash

# uruchom klaster ZK
docker run -d \
   --net=host \
   --name=zzk-1 \
   -e ZOOKEEPER_SERVER_ID=1 \
   -e ZOOKEEPER_CLIENT_PORT=22181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="localhost:22888:23888;localhost:32888:33888;localhost:42888:43888" \
   confluentinc/cp-zookeeper:latest

docker run -d \
   --net=host \
   --name=zzk-2 \
   -e ZOOKEEPER_SERVER_ID=2 \
   -e ZOOKEEPER_CLIENT_PORT=32181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="localhost:22888:23888;localhost:32888:33888;localhost:42888:43888" \
   confluentinc/cp-zookeeper:latest

docker run -d \
   --net=host \
   --name=zzk-3 \
   -e ZOOKEEPER_SERVER_ID=3 \
   -e ZOOKEEPER_CLIENT_PORT=42181 \
   -e ZOOKEEPER_TICK_TIME=2000 \
   -e ZOOKEEPER_INIT_LIMIT=5 \
   -e ZOOKEEPER_SYNC_LIMIT=2 \
   -e ZOOKEEPER_SERVERS="localhost:22888:23888;localhost:32888:33888;localhost:42888:43888" \
   confluentinc/cp-zookeeper:latest

docker ps

# sprawdź czy poprawnie uruchomiony
nc -vz localhost 22181
nc -vz localhost 32181
nc -vz localhost 42181

docker logs zk-1

# sprawdź konfiguracje ZK
less /etc/kafka/zookeeper.properties

# sprawdź serwer ID
less /var/lib/zookeeper/data/myid

# interakcja przez ZK shell
zookeeper-shell localhost:22181
ls /

# zmiany na jednym węźle powinny być widoczne na pozostałych węzłach
create /mynode "test data"

# sprawdź na innym węźle
ls /

# REFERENCES
# cluster deployment https://docs.confluent.io/latest/installation/docker/docs/installation/clustered-deployment.html