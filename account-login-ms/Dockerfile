FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/account-login-docker.jar
COPY ${JAR_FILE} account-login-docker.jar
ENTRYPOINT ["java","-jar","/account-login-docker.jar"]