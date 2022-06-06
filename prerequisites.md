## INSTRUKCJA INSTALACJI ŚRODOWISKA

Przygotowane warsztaty powinny działać niezależenie od systemu operacyjnego pod warunkiem instalacji narzędzia do konteneryzacji.
Należy zainstalować następujące komponenty:

* JAVA JDK w wersji >= 1.8
    
    Zweryfikowanie poprawności instalacji w Bash/Shell lub CMD/Powershell za pomocą następującej komendy:    
    
        $ java -version
        
    Przykład wykonania:
    
        openjdk version "1.8.0_252"
        OpenJDK Runtime Environment (build 1.8.0_252-8u252-b09-1ubuntu1-b09)
        OpenJDK 64-Bit Server VM (build 25.252-b09, mixed mode)
        
    Przydatne linki:
    
    *   https://java.com/en/download/help/download_options.xml
                
* Maven >= 3.6

    Zweryfikowanie poprawności instalacji w Bash/Shell lub CMD/Powershell za pomocą następującej komendy:
        $ mvn --version
        
    Przykład wykonania:
    
        Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T20:41:47+02:00)
        Maven home: /opt/maven
        Java version: 1.8.0_252, vendor: Private Build, runtime: /usr/lib/jvm/java-8-openjdk-amd64/jre
        Default locale: en_US, platform encoding: UTF-8
        OS name: "linux", version: "5.4.0-33-generic", arch: "amd64", family: "unix"

    Przydatne linki:
    
    *   https://maven.apache.org/install.html

* Docker >= 19.0

    Zweryfikowanie poprawności instalacji w Bash/Shell lub CMD/Powershell za pomocą następującej komendy:    
    
        $ docker --version
        
    Przykład wykonania:
        
        Docker version 19.03.8, build afacb8b7f0
        
    Sprawdzenie czy klient `docker` ma możliwość pobrania obrazu z `docker hub` i czy poprawnie się uruchomił
        
        $ docker run --net=host --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181-e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper:6.1.4

    Sprawdzenie czy sieć typu docker bridge jest dostępna z poziomu maszyny hostującej

        $docker network create mynetwork
        $docker network inspect mynetwork

    Przykład wykonania poniżej. Docker powinien wystawić adres IP "Gateway" jak w przykładzie poniżej
    ```json
    [
        {
            "Name": "mynetwork",
            "Id": "6c37c7036e75577a311b3296f168f9a3bb85116f6ac8d9bbfac0486f03c1ec3f",
            "Created": "2022-05-25T12:32:04.256044191+02:00",
            "Scope": "local",
            "Driver": "bridge",
            "EnableIPv6": false,
            "IPAM": {
                "Driver": "default",
                "Options": {},
                "Config": [
                    {
                        "Subnet": "172.27.0.0/16",
                        "Gateway": "172.27.0.1"
                    }
                ]
            },
            "Internal": false,
            "Attachable": false,
            "Ingress": false,
            "ConfigFrom": {
                "Network": ""
            },
            "ConfigOnly": false,
            "Containers": {},
            "Options": {},
            "Labels": {}
        }
    ]
    
    ```

    Przydatne linki:
    
    *   https://docs.docker.com/docker-for-windows/install/ 


*   IntelliJ IDEA w wersji `community`

    Przydatne linki:
    
    *   https://www.jetbrains.com/idea/

