# --- BUILD ---
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app
COPY . .

RUN ./gradlew bootJar --no-daemon -x test

# --- Running---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
