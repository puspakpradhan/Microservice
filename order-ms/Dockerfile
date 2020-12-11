FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/order-docker.jar
COPY ${JAR_FILE} order-docker.jar
ENTRYPOINT ["java","-jar","/order-docker.jar"]