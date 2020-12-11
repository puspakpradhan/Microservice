FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/product-docker.jar
COPY ${JAR_FILE} product-docker.jar
ENTRYPOINT ["java","-jar","/product-docker.jar"]