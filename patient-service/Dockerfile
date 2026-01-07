FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built jar
COPY target/patient-service.jar app.jar

# Environment variables for Spring Boot and timezone
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5000/patientdb
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=secret

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]

