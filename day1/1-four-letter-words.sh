#!/bin/bash

# https://zookeeper.apache.org/doc/r3.4.7/zookeeperAdmin.html#sc_zkCommands

# wyświetl konfiguracje
echo "conf" | nc localhost 32181

# wyświetl połączenia
echo "cons" | nc localhost 2181

# wyświetl detale środowiska
echo "envi" | nc localhost 2181


# healthcheck
echo "ruok" | nc localhost 2181

# wyświetl detale serwera
echo "srvr" | nc localhost 32181

# statystki serwera i połączeń
echo "stat" | nc localhost 2181

# wyświetl "obserwujących'
echo "wchs" | nc localhost 2181

# wyświetl szczegóły obserwujących w ramach sesji
echo "wchc" | nc localhost 2181

# wyświetl szczegóły obserwujących w ramach ścieżki
echo "wchp" | nc localhost 2181

# wyświetl zmienne środowiskowe służące do sprawdzenia czy klaster jest działający
echo "mntr" | nc localhost 2181
