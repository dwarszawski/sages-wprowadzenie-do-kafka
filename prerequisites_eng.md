## SETTING UP DEV ENVIRONMENT

The prepared source code and scripts should work on any OS assuming that docker is installed properly.

Following components must be installed:

*   JAVA JDK version >= 1.8
    
    Verify if `jdk` is available on the path with Bash/Shell or CMD/Powershell:    
    
        $ java -version
        
    Sample output of running command:
    
        openjdk version "1.8.0_252"
        OpenJDK Runtime Environment (build 1.8.0_252-8u252-b09-1ubuntu1-b09)
        OpenJDK 64-Bit Server VM (build 25.252-b09, mixed mode)
        
    Useful links:
    
    *   https://java.com/en/download/help/download_options.xml
                
*   Maven >= 3.6

    Verify if `maven` is installed properly with Bash/Shell or CMD/Powershell:
        $ mvn --version

    Sample output of running command:
    
        Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T20:41:47+02:00)
        Maven home: /opt/maven
        Java version: 1.8.0_252, vendor: Private Build, runtime: /usr/lib/jvm/java-8-openjdk-amd64/jre
        Default locale: en_US, platform encoding: UTF-8
        OS name: "linux", version: "5.4.0-33-generic", arch: "amd64", family: "unix"

    Useful links:
    
    *   https://maven.apache.org/install.html

*   Docker >= 19.0

    Verify if `docker` is installed properly with Bash/Shell or CMD/Powershell:    
    
        $ docker --version
        
    Sample output of running command:
        
        Docker version 19.03.8, build afacb8b7f0
        
    Check if `docker` client can download image from public `docker hub` 
        
        $ docker run --net=host --name=zookeeper \
        -e ZOOKEEPER_CLIENT_PORT=32181 \
        -e ZOOKEEPER_TICK_TIME=2000 \
        -e ZOOKEEPER_SYNC_LIMIT=2 \
        confluentinc/cp-zookeeper:6.1.4
        
    Useful links:
    
    *   https://docs.docker.com/docker-for-windows/install/ 


* IntelliJ IDEA `community` or `enterprise` version

    Useful links:
    
    *   https://www.jetbrains.com/idea/

* OpenSSL cli

    Useful links:

    * https://www.cloudinsidr.com/content/how-to-install-the-most-recent-version-of-openssl-on-windows-10-in-64-bit/
