# base stage
FROM maven:3.6.0-jdk-8-slim AS base
RUN mkdir -p /workspace
WORKDIR /workspace
COPY .mvn/ .mvn
COPY mvnw pom.xml /workspace/
RUN mvn dependency:go-offline
COPY src /workspace/src

# test stage
FROM base as test
RUN mvn test

# build stage
FROM base as build
RUN mvn -f pom.xml clean package

# run stage
FROM openjdk:8-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]