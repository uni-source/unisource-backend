FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} student-service.jar
ENTRYPOINT ["java", "-jar", "/student-service.jar"]
EXPOSE 8084
