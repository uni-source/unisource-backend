FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} admin-service.jar
ENTRYPOINT ["java", "-jar", "/admin-service.jar"]
EXPOSE 8082
