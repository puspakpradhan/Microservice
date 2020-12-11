FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/cart-docker.jar
COPY ${JAR_FILE} cart-docker.jar
ENTRYPOINT ["java","-jar","/cart-docker.jar"]