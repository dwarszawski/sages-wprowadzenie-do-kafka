#!/bin/bash

# interaktywny shell dla ZK
zookeeper-shell localhost:32181

# wyświetl pomoc
help
# wyświetl "root"
ls /

# utwórz nowy "znode"
create /my-node "foo"
ls /

get /my-node
get /zookeeper
create /my-node/deeper-node "bar"
ls /
ls /my-node
ls /my-node/deeper-node
get /my-node/deeper-node

# zmodyfikuj wartość dla danej ścieżki
set /my-node/deeper-node "newdata"
get /my-node/deeper-node
# removes are recursive
rmr /my-node
ls /
# create a watcher
create /node-to-watch ""
get /node-to-watch true
set /node-to-watch "hhhas-changeddddd"
deleteall /node-to-watch
