docker run -d --hostname zzk-3 --add-host=zzk-1:172.31.166.121 \
 --add-host=zzk-2:172.31.166.121 \
 -p 42181:42181 \
 -p 43888:43888 \
 -p 42888:42888 \
 --name=zzk-3 \
 -e ZOOKEEPER_SERVER_ID=3 \
 -e ZOOKEEPER_CLIENT_PORT=42181 \
 -e ZOOKEEPER_TICK_TIME=2000 \
 -e ZOOKEEPER_INIT_LIMIT=5 \
 -e ZOOKEEPER_SYNC_LIMIT=2 \
 -e ZOOKEEPER_SERVERS="zzk-1:22888:23888;zzk-2:32888:33888;zzk-3:42888:43888" \
 confluentinc/cp-zookeeper:latest