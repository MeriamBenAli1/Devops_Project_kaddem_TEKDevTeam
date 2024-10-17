FROM openjdk:17-jdk-alpine

ADD target/kaddem-0.0.1-SNAPSHOT.jar kaddem.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kaddem.jar"]
