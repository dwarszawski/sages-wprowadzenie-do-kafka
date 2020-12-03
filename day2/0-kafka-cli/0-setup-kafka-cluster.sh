# kafka cluster quick start

# copy file to local working dir
https://raw.githubusercontent.com/confluentinc/cp-docker-images/5.3.1-post/examples/kafka-single-node/docker-compose.yml

# run docker image with docker-compose
docker-compose up

# find container id of kafka cluster
docker ps

# open bash to kafka cluster container
docker exec -it 17856600dec8 /bin/bash
