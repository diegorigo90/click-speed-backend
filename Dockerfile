FROM openjdk:17-jdk-slim
MAINTAINER test
COPY /target/speedclick-0.0.1-SNAPSHOT.jar speedclick-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/speedclick-0.0.1-SNAPSHOT.jar"]