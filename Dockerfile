# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-alpine

EXPOSE 8089

COPY target/*.jar kaddem.jar

CMD ["java", "-jar", "kaddem.jar"]