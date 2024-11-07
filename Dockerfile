FROM openjdk:17
EXPOSE 8089
ADD target/kaddem-0.0.1-SNAPSHOT.jar kaddem.jar
CMD ["java", "-jar", "kaddem.jar"]
