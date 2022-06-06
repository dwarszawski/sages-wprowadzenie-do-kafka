#!/bin/bash

# Enter one f the Zookeeper containers
docker exec -it zzk-1 /bin/bash

# Run interactive shell and connect to Zookeeper
zookeeper-shell localhost:32181

# Display help
help
# Display root
ls /

# Create Znode API
create /my-node "foo"
ls /

get /my-node
get /zookeeper
create /my-node/deeper-node "bar"
ls /
ls /my-node
ls /my-node/deeper-node
get /my-node/deeper-node

# Modify metadata
set /my-node/deeper-node "newdata"
get /my-node/deeper-node


# Create a watcher to track the znode changes
create /node-to-watch ""
get /node-to-watch true
set /node-to-watch "hhhas-changeddddd"

# Delete a znode
deleteall /node-to-watch
