#!/bin/bash

# dynamicznie zmień konfiguracje klastra - TODO check if entity name is correct
docker run \
    --net=host \
    --rm \
    confluentinc/cp-kafka:5.0.0 \
    kafka-configs --bootstrap-server localhost:29092 --add-config unclean.leader.election.enable=false --alter --entity-name 1 --entity-type brokers

