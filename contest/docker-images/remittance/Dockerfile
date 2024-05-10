# Base image
FROM openjdk:18.0-slim

# Set working directory
WORKDIR /app

# Copy Gradle build files
COPY build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
