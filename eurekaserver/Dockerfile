FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} eurekaserver-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/eurekaserver-0.0.1-SNAPSHOT.jar"]