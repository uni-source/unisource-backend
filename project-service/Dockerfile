FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} project-service.jar
ENTRYPOINT ["java", "-jar", "/project-service.jar"]
EXPOSE 8085
