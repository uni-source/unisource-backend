FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} organization-service.jar
ENTRYPOINT ["java", "-jar", "/organization-service.jar"]
EXPOSE 8083
