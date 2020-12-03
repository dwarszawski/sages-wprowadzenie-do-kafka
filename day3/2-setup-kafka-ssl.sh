#!/usr/bin/env bash

# /etc/hosts should simulate dns -> 127.0.0.1       localhost.example.com

mkdir tmpgenkey
cd tmp
# create a server certificate !! put your public DNS after "CN="

export SRVPASS=secureme
keytool -genkey -keystore kafka.server.keystore.jks -validity 365 -storepass $SRVPASS -keypass $SRVPASS  -dname "CN=localhost.example.com" -storetype pkcs12
ls
keytool -list -v -keystore kafka.server.keystore.jks

# create a certification request file, to be signed by the CA
# should be sent through the email to CA authority
keytool -keystore kafka.server.keystore.jks -certreq -file cert-file -storepass $SRVPASS -keypass $SRVPASS
ls

# sign the server certificate
openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS
ls

## check certificates
keytool -printcert -v -file cert-signed
keytool -list -v -keystore kafka.server.keystore.jks

# Trust the CA by creating a truststore and importing the ca-cert
keytool -keystore kafka.server.truststore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt

# Import CA and the signed server certificate into the keystore
keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt
keytool -keystore kafka.server.keystore.jks -import -file cert-signed -storepass $SRVPASS -keypass $SRVPASS -noprompt

echo $SRVPASS > ssl-key-creds
echo $SRVPASS > keystore-creds
echo $SRVPASS > truststore-creds


# Adjust Broker configuration  
docker build -f ../2-setup-dockerfile -t kafka-ssl-encrypted ../
docker run --net=host -e KAFKA_ZOOKEEPER_CONNECT=localhost.example.com:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost.example.com:9092,SSL://localhost.example.com:9093 -e KAFKA_SSL_KEYSTORE_FILENAME=ssl/kafka.server.keystore.jks -e KAFKA_SSL_KEYSTORE_CREDENTIALS=keystore-creds -e KAFKA_SSL_KEY_CREDENTIALS=ssl-key-creds -e KAFKA_SSL_TRUSTSTORE_FILENAME=ssl/kafka.server.truststore.jks -e KAFKA_SSL_TRUSTSTORE_CREDENTIALS=truststore-creds kafka-ssl-encrypted

# Verify SSL config from your local computer
openssl s_client -connect localhost.example.com:9093
