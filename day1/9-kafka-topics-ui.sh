#!/bin/bash

# launch it - TODO check how to expose rest proxy
docker run --rm -it -p 8000:8000 \
           -e "KAFKA_REST_PROXY_URL=http://localhost:29092" \
           landoop/kafka-topics-ui

# REFERENCES
# https://hub.docker.com/r/landoop/kafka-topics-ui/