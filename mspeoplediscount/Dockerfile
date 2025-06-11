FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} mspeoplediscount-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/mspeoplediscount-0.0.1-SNAPSHOT.jar"]