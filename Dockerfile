# Build stage
FROM maven:3.9.0-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Launch java -jar
FROM amazoncorretto:17
MAINTAINER test
COPY --from=build /home/app/target/speedclick-0.0.1-SNAPSHOT.jar speedclick-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=database,test","/speedclick-0.0.1-SNAPSHOT.jar"]
