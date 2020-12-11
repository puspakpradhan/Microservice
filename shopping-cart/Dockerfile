FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/shopping-docker.jar
COPY ${JAR_FILE} shopping-docker.jar
ENTRYPOINT ["java","-jar","/shopping-docker.jar"]