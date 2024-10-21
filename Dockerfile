# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17
EXPOSE 8089
ADD target/*.jar kaddem.jar
CMD ["java", "-jar", "kaddem.jar"]