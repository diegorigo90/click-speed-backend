FROM amazoncorretto:17
MAINTAINER test
COPY /target/speedclick-0.0.1-SNAPSHOT.jar speedclick-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/speedclick-0.0.1-SNAPSHOT.jar"]
