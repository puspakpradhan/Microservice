FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/eureka-server-docker.jar
COPY ${JAR_FILE} eureka-server-docker.jar
ENTRYPOINT ["java","-jar","/eureka-server-docker.jar"]