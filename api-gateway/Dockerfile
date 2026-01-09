# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first (dependency cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre AS runner

WORKDIR /app

# Copy correct JAR name
COPY --from=builder /app/target/api-gateway.jar app.jar

# HTTP (Spring Boot)
EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t billing-service .
