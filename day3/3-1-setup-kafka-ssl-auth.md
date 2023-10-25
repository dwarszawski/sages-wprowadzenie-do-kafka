# Enable SSL authentication (configuration of Kafka client)

## create a CLIENT certificate !! put your LOCAL hostname after "CN=" and specify an alias

mkdir ssl
cd ssl

* Generate keystore with client certificate

```text
export CLIPASS=clientpass
keytool -genkey -keyalg RSA -keystore kafka.client.keystore.jks -validity 365 -storepass $CLIPASS -keypass $CLIPASS  -dname "CN=kafkaclient" -alias kafkaclient -storetype pkcs12

```

* Show the content of the keystore
```text
keytool -list -v -keystore kafka.client.keystore.jks
```

* Create a Certificate Signing Request CSR

```text
keytool -keystore kafka.client.keystore.jks -certreq -file client-cert-sign-request -alias kafkaclient -storepass $CLIPASS -keypass $CLIPASS
```

* Create self-signed certificate - simulate the process of sending server certificate to CA authority

```text
export SRVPASS=secureme
openssl x509 -req -CA ../tmp/ca-cert -CAkey ../tmp/ca-key -in client-cert-sign-request -out client-cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS
```

* Import CA root cert into the Keystore
```text
keytool -keystore kafka.client.keystore.jks -alias CARoot -import -file ../tmp/ca-cert -storepass $CLIPASS -keypass $CLIPASS -noprompt
```

* Import signed client certificate into the Keystore
```text
keytool -keystore kafka.client.keystore.jks -import -file client-cert-signed -alias kafkaclient -storepass $CLIPASS -keypass $CLIPASS -noprompt
```

* Verify content of the keystore
```text
keytool -list -v -keystore kafka.client.keystore.jks
```

* Run Zookeeper container
```text
docker run -d --network mynetwork --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:7.3.2
```

* Run Kafka brokers with SSL authentication enabled

```text
docker build -f ../3-1-setup-server-dockerfile -t kafka-ssl-auth ../
# -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL 
docker run -d --network mynetwork --name=kafkabroker --hostname=kafkabroker -p 9093:9093 -e KAFKA_OPTS="-Djavax.net.debug=ssl" -e KAFKA_SSL_CLIENT_AUTH=required -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafkabroker:9092,SSL://kafkabroker:9093 -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 kafka-ssl-encrypted 

docker ps

```

* Build kafka client docker image

make sure that file `3-1-setup-client-dockerfile` is in the parent directory of `ssl`
make sure that file `3-2-setup-client-ssl.properties` is in the parent directory of `ssl`

```text
cd ../
docker build -f ./3-2-setup-client-dockerfile -t kafka-ssl-client .
```
``

* Verify SSL authentication with Kafka producer

```text

docker run -it  --net=mynetwork kafka-ssl-client /bin/bash
kafka-topics --bootstrap-server  kafkabroker:9093  --create --replication-factor 1 --partitions 1 --topic messages  --command-config /tmp/client-ssl.properties
kafka-console-producer --broker-list kafkabroker:9093 --topic messages --producer.config /tmp/client-ssl.properties

```

* Verify SSL authentication with Kafka consumer

```text
docker run -it --net=mynetwork   kafka-ssl-client /bin/bash

kafka-console-consumer --bootstrap-server kafkabroker:9093 --topic messages --consumer.config /tmp/client-ssl.properties

```

