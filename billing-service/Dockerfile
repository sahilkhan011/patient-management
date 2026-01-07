# --------------- BUILD STAGE
FROM maven:3.9.9-eclipse-temurin-21 as builder

WORKDIR /app

# copy pom file for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy source and build
COPY src  ./src
RUN mvn clean package -DskipTests


# ------------- RUNTIME STAGE
FROM eclipse-temurin-21-jre

WORKDIR /app

COPY --from=builder /app/target/billing-service.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/app.jar" ]