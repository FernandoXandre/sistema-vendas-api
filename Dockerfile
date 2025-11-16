FROM maven:3.9.5-amazoncorretto-21 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=target/*.jar

EXPOSE 8080

WORKDIR /app

COPY --from=build ${JAR_FILE} app.jar

ENV JAVA_TOOL_OPTIONS="-Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["java", "-jar", "app.jar"]