# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy only the built jar from builder
COPY --from=builder /app/target/patient-service.jar app.jar

# (Optional but recommended) JVM timezone
#ENV TZ=Asia/Kolkata

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

# docker build -t patient-service .
