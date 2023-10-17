FROM maven:3.8.4-openjdk-11 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

ENV APP_HOME /app
ENV APP_NAME api.jar

COPY --from=builder /app/target/${APP_NAME} ${APP_HOME}/${APP_NAME}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/api.jar"]
