#!/bin/bash

docker run \
  -d --network host \
  -e HTTP_PORT=9000 \
  --name zoonavigator \
  --restart unless-stopped \
  elkozmon/zoonavigator:latest

# Reference
# https://hub.docker.com/r/elkozmon/zoonavigator