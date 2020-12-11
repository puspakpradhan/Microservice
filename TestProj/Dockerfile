FROM openjdk:8-jdk-alpine
ARG JAR_FILE=zipkin-server-2.12.9-exec.jar
COPY ${JAR_FILE} zipkin-server-2.12.9-exec.jar
ENTRYPOINT ["java","-jar","/zipkin-server-2.12.9-exec.jar"]