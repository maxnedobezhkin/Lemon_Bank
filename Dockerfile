#FROM ubuntu:latest
#LABEL authors="Admin"
#
#ENTRYPOINT ["top", "-b"]

#FROM maven:3.8.5-eclipse-temurin-17 as build
#WORKDIR /build
#COPY src src
#COPY pom.xml pom.xml
#RUN --mount=type=cache,target=/root/.m2 mvn clean package dependency:copy-dependencies -DincludeScope=runtime
#
#FROM bellsoft/liberica-openjdk-debian:17
#RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot
#USER spring-boot:spring-boot-group
#
#VOLUME /tmp
#ARG JAR_FILE=target
#WORKDIR /application
#
#CMD ["java", "-jar", "target/Lemon_Bank-0.0.1-SNAPSHOT.jar"]

FROM maven:3.8.5-eclipse-temurin-17 AS build

#RUN apk add --no-cache git

COPY . /app


WORKDIR /app
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests dependency:copy-dependencies -DincludeScope=runtime

#ENV GITHUB_REPO_URL=https://github.com/maxnedobezhkin/Lemon_Bank

#RUN rm -rf /app/*

#RUN git clone $GITHUB_REPO_URL && git checkout develop

#RUN chmod +x ./mvnw

#RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/Lemon_Bank-0.0.1-SNAPSHOT.jar"]