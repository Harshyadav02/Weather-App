# Weather Application

## Description

The Weather Application is a Spring Boot project designed to provide weather information. It includes a basic setup with controllers and services, and utilizes Thymeleaf templates for rendering views. The application is structured to handle basic weather data retrieval and presentation.

## Project Structure
```sh 
.
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── harsh
    │   │           └── weather
    │   │               ├── controller
    │   │               │   └── WeatherController.java
    │   │               ├── service
    │   │               │   └── WeatherService.java
    │   │               └── WeatherApplication.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       │   └── style.css
    │       └── templates
    │           └── weather.html
    └── test
        └── java
            └── com
                └── harsh
                    └── weather
                        └── WeatherApplicationTests.java
```

## Features

- **Controller**: `WeatherController.java` - Handles HTTP requests related to weather information.
- **Service**: `WeatherService.java` - Contains business logic for processing weather data.
- **Application**: `WeatherApplication.java` - Entry point of the Spring Boot application.
- **Templates**: `weather.html` - Thymeleaf template for displaying weather data.
- **Static Resources**: `style.css` - Stylesheet for application styling.

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven

### Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/Harshyadav02/Weather-App.git
    ```

2. Navigate to the project directory:

    ```sh
    cd Weather-App
    ```

3. Build the project using Maven:

    ```sh
    ./mvnw clean install
    ```

4. Run the application:

    ```sh
    ./mvnw spring-boot:run
    ```

5. Open your web browser and go to `http://localhost:8080` to see the application in action.

## Configuration

The application configuration can be modified in the `src/main/resources/application.properties` file.

## Testing

To run tests, use the following Maven command:

```sh
./mvnw test
```
## Contributing
If you would like to contribute to the project, please fork the repository and submit a pull request with your changes.

