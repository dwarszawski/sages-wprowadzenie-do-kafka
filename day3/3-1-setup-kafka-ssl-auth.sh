# on client machine

## create a CLIENT certificate !! put your LOCAL hostname after "CN=" and specify an alias

export CLIPASS=clientpass
mkdir ssl
cd ssl

keytool -genkey -keystore kafka.client.keystore.jks -validity 365 -storepass $CLIPASS -keypass $CLIPASS  -dname "CN=dwarszawski-XPS-15-9570" -alias dwarszawski -storetype pkcs12
keytool -list -v -keystore kafka.client.keystore.jks

## create a certification request file, to be signed by the CA
keytool -keystore kafka.client.keystore.jks -certreq -file client-cert-sign-request -alias dwarszawski -storepass $CLIPASS -keypass $CLIPASS


## copy the request file to your CA
export SRVPASS=secureme
openssl x509 -req -CA ../tmp/ca-cert -CAkey ../tmp/ca-key -in client-cert-sign-request -out client-cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS


# switch back to local computer
keytool -keystore kafka.client.keystore.jks -alias CARoot -import -file ../tmp/ca-cert -storepass $CLIPASS -keypass $CLIPASS -noprompt
keytool -keystore kafka.client.keystore.jks -import -file client-cert-signed -alias dwarszawski -storepass $CLIPASS -keypass $CLIPASS -noprompt

keytool -list -v -keystore kafka.client.keystore.jks

# run zookeeper
docker run -d --net=host --name=zookeeper \
-e ZOOKEEPER_CLIENT_PORT=32181 \
-e ZOOKEEPER_TICK_TIME=2000 \
-e ZOOKEEPER_SYNC_LIMIT=2 \
confluentinc/cp-zookeeper:latest


# configure Kafka Broker
docker build -f ../3-1-setup-server-dockerfile -t kafka-ssl-auth ../
docker run -d --name=ssl-kafka-1 --net=host -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL -e KAFKA_SSL_CLIENT_AUTH=required -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 -e KAFKA_ADVERTISED_LISTENERS=SSL://localhost.example.com:19093 -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds  kafka-ssl-auth
docker run -d --name=ssl-kafka-2 --net=host -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL -e KAFKA_SSL_CLIENT_AUTH=required -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 -e KAFKA_ADVERTISED_LISTENERS=SSL://localhost.example.com:29093 -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds  kafka-ssl-auth
docker run -d --name=ssl-kafka-3 --net=host -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL -e KAFKA_SSL_CLIENT_AUTH=required -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 -e KAFKA_ADVERTISED_LISTENERS=SSL://localhost.example.com:39093 -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds  kafka-ssl-auth
docker ps
# -e KAFKA_SECURITY_INTER_BROKER_PROTOCOL=SSL

# TEST
docker build -f ../3-2-setup-client-dockerfile -t kafka-ssl-client ../

# run container in new terminal
docker run -it --net=host  kafka-ssl-client /bin/bash
# create topic
kafka-topics --bootstrap-server localhost.example.com:19093,localhost.example.com:29093,localhost.example.com:39093  --create --topic secured-topic  --command-config /tmp/client-ssl.properties

# within container
kafka-console-consumer --bootstrap-server localhost.example.com:19093,localhost.example.com:29093,localhost.example.com:39093 --topic secured-topic --consumer.config /tmp/client-ssl.properties

# run container in new terminal
docker run -it  --net=host  kafka-ssl-client /bin/bash
# within container
kafka-console-producer --broker-list localhost.example.com:19093,localhost.example.com:29093,localhost.example.com:39093 --topic secured-topic --producer.config /tmp/client-ssl.properties


