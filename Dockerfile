FROM gradle:8.4-jdk21 AS builder

WORKDIR /app
COPY --chown=gradle:gradle . .

RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]