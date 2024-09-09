FROM maven:3.8.4-openjdk-17

COPY . /project
RUN  cd /project

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/kafka-producer-1.0-SNAPSHOT.jar"]