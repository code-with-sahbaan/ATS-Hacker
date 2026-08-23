FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/app-service-1.0.0.jar app.jar

EXPOSE 8080

# Limit JVM heap to 256MB/384MB and thread stack size
ENTRYPOINT ["java", "-Xmx384m", "-Xss512k", "-jar", "app.jar"]