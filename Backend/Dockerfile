FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy POM files first for better Docker layer caching
COPY pom.xml .
COPY BusinessModule/pom.xml Business/pom.xml
COPY DataModule/pom.xml Data/pom.xml
COPY SecurityModule/pom.xml Security/pom.xml
COPY app-service/pom.xml app-service/pom.xml

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY BusinessModule/src Business/src
COPY DataModule/src Data/src
COPY SecurityModule/src Security/src
COPY app-service/src app-service/src

# Build all modules
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/app-service/target/app-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx384m", "-Xss512k", "-jar", "app.jar"]