FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy POM files first for better Docker layer caching
COPY pom.xml .
COPY BusinessModule/pom.xml BusinessModule/pom.xml
COPY DataModule/pom.xml DataModule/pom.xml
COPY SecurityModule/pom.xml SecurityModule/pom.xml
COPY app-service/pom.xml app-service/pom.xml

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY BusinessModule/src BusinessModule/src
COPY DataModule/src DataModule/src
COPY SecurityModule/src SecurityModule/src
COPY app-service/src app-service/src

# Build all modules
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/app-service/target/app-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx384m", "-Xss512k", "-jar", "app.jar"]