# start zookeeper
docker run -d \
    --net=host \
    --name=zookeeper \
    -e ZOOKEEPER_CLIENT_PORT=32181 \
    -e ZOOKEEPER_TICK_TIME=2000 \
    confluentinc/cp-zookeeper:latest

# start kafka cluster
docker run -d \
    --net=host \
    --name=kafka \
    -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:29092 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    confluentinc/cp-kafka:latest


# create kafka connect topics
# download custom connector
git clone https://github.com/dwarszawski/airflow-sink-connector.git

# build connector with maven
mvn clean install

# start standalone kafka connect
docker run -d \
  --name=kafka-connect \
  --net=host \
  -e CONNECT_BOOTSTRAP_SERVERS=localhost:29092 \
  -e CONNECT_REST_PORT=28083 \
  -e CONNECT_GROUP_ID="connect" \
  -e CONNECT_CONFIG_STORAGE_TOPIC="quickstart-config" \
  -e CONNECT_OFFSET_STORAGE_TOPIC="quickstart-offsets" \
  -e CONNECT_STATUS_STORAGE_TOPIC="quickstart-status" \
  -e CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR=1 \
  -e CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR=1 \
  -e CONNECT_STATUS_STORAGE_REPLICATION_FACTOR=1 \
  -e CONNECT_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
  -e CONNECT_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
  -e CONNECT_INTERNAL_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
  -e CONNECT_INTERNAL_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" \
  -e CONNECT_REST_ADVERTISED_HOST_NAME="localhost" \
  -e CONNECT_LOG4J_ROOT_LOGLEVEL=DEBUG \
  -e CONNECT_PLUGIN_PATH=/tmp/connectors \
  -v /tmp/connectors/jars:/etc/kafka-connect/jars \
  confluentinc/cp-kafka-connect:latest

# list running connectors
curl -s -X GET http://localhost:28083/connectors/

# ensure kafka-connect started
docker logs kafka-connect | grep started

# ifconfig - ip of docker interface
docker run --rm -it -p 8000:8000 \
           -e "CONNECT_URL=http://172.17.0.1:28083" \
           landoop/kafka-connect-ui

# generate template kafka-connector template
# https://github.com/jcustenborder/kafka-connect-archtype


# register a new connector
#connector.class=com.dwarszawski.airflowsink.AirflowSinkConnector
#airflow.password=airflow
#topics=test
#tasks.max=1
#airflow.endpoint=http://127.0.0.1:8080
#airflow.dag.id=example_python_operator
#airflow.dag.run.id=aaa
#airflow.username=airflow
