FROM openjdk:8-jdk-alpine
COPY target/ms-try-0.0.1-SNAPSHOT.jar orchestration-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ms-try-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080