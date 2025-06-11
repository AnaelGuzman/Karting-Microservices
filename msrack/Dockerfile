FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} msrack-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/msrack-0.0.1-SNAPSHOT.jar"]