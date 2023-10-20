# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/uselesssystem-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Set environment variables (you can customize these)
ENV SPRING_REDIS_HOST=redis
ENV SPRING_REDIS_PORT=6379

# Start the Spring Boot application
CMD ["java", "-jar", "app.jar"]