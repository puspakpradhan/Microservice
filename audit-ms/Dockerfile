FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/audit-docker.jar
COPY ${JAR_FILE} audit-docker.jar
ENTRYPOINT ["java","-jar","/audit-docker.jar"]