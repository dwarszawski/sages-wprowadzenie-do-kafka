FROM maven:3.5.4-jdk-8

COPY . /project
RUN  cd /project

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/target/6-kafka-streams-1.0-SNAPSHOT.jar"]