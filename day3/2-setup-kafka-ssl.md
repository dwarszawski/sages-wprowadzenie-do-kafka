## Enable SSL encryption

### Workaround to simulate DNS 

```text
vim /etc/hosts

${IP_GATEWAY}      localhost.example.com
```

### Create a broker Keystore and Truststore

* Create workdir

```text
mkdir tmp
cd tmp
```

* Generate keystore with server certificate

```text
export SRVPASS=secureme

# put hostname as defined in /etc/hosts after "CN="
keytool -genkey -keyalg RSA -keystore kafka.server.keystore.jks -validity 365 -storepass $SRVPASS -keypass $SRVPASS  -dname "CN=kafkabroker" -storetype pkcs12

ls
```

* Show the content of the keystore

```text
keytool -list -v -keystore kafka.server.keystore.jks
```

* Create a Certificate Signing Request CSR

```text

keytool -keystore kafka.server.keystore.jks -certreq -file cert-file -storepass $SRVPASS -keypass $SRVPASS

ls

```


* Create self-signed certificate - simulate the process of sending server certificate to CA authority

```text

openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS
ls

```

* Print certificate

```text
keytool -printcert -v -file cert-signed

```

* Generate Trust Store with root CA cert

```text
keytool -keystore kafka.server.truststore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt

```

* Import CA root cert into the Keystore

```text
keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt
```

* Import signed server certificate into the Keystore

```text
keytool -keystore kafka.server.keystore.jks -import -file cert-signed -storepass $SRVPASS -keypass $SRVPASS -noprompt

```

* Verify content of keystore
```text
keytool -list -v -keystore kafka.server.keystore.jks

```

### Adjust Broker configuration  

* Prepare files to store credentials

```text
echo $SRVPASS > ssl-key-creds
echo $SRVPASS > keystore-creds
echo $SRVPASS > truststore-creds
```

* Build docker image

make sure that file `3-1-setup-server-dockerfile` is in the parent directory of `tmp` 

```text
cd ../
docker build -f ./3-1-setup-server-dockerfile -t kafka-ssl-encrypted .

```

* Run Kafka broker with SSL encryption

```text

docker run -d --network mynetwork --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:7.3.2
docker run -d --network mynetwork --hostname=kafkabroker --name=kafkabroker -p 9093:9093  --add-host localhost.example.com:${IP_GATEWAY} -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafkabroker:9092,SSL://localhost.example.com:9093 -e KAFKA_SSL_KEYSTORE_FILENAME=kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds kafka-ssl-encrypted

```

* Verify SSL encryption to broker

```text
openssl s_client -connect ${IP_GATEWAY}:9093
```
