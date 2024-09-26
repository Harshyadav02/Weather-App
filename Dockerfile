# Use the official Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build it
COPY . .
RUN mvn clean package -DskipTests

# Use an OpenJDK runtime image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/weather-0.0.1-SNAPSHOT.jar /app/weather.jar

# Expose port 8080 (or whichever port your Spring Boot app uses)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/weather.jar"]
