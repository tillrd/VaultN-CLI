# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project files to the working directory
COPY . /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Package the application using Maven
RUN mvn clean package -DskipTests

# Expose the port the application runs on (if needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/vaultn-cli-1.0-SNAPSHOT.jar"]