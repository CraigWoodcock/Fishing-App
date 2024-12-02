- [Fishing Session Tracker 🎣](#fishing-session-tracker-)
  - [Documentation](#documentation)
  - [Features](#features)
  - [Tech Stack](#tech-stack)
  - [Deployment](#deployment)
    - [Workflow Outline with ASG:](#workflow-outline-with-asg)
  - [Project Structure](#project-structure)
  - [Key Components](#key-components)
    - [Web Interface](#web-interface)
    - [REST API](#rest-api)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Upcoming Features](#upcoming-features)
  - [Security](#security)

# Fishing Session Tracker 🎣

A Spring Boot application for tracking fishing sessions, managing catches, and analyzing fishing history. Features both
a web interface and REST API support.

## Documentation

- [Click here to view documentation ](https://craigwoodcock.github.io/Fishing-App/)

## Features

- 🔐 User authentication and authorization
- 📝 Create and manage fishing sessions
- 🐟 Log catches with detailed information
- 📊 View fishing history and statistics
- 📱 REST API support for mobile integration
- 📸 Photo upload functionality (coming soon)

## Tech Stack

- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Spring Boot 3.3.2](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Tailwind CSS 3.3.2](https://tailwindcss.com/)
- [MySQL](https://www.mysql.com/)
- [Maven](https://maven.apache.org/)
- AWS S3 (upcoming)

## Deployment

- Github(for version control)
- Github Actions(triggers a push to Gitlab-CI autonomously)
- Gitlab-CI(to automate build, test and deployment to AWS)
- AWS S3(for .jar storage)
- AWS RDS(for database instance)
- AWS VPC(virtual private cloud - ensures secure instances)
- AWS ASG(auto scaling group – to achieve zero downtime when pushing updates)

### Workflow Outline with ASG:
1.	Code Commit: Push code to GitHub and trigger the GitHub Actions workflow.
2.	Build and Test: Build the JAR in GitLab CI, run tests, and upload the artifact to S3.
3.	Update ASG: Update the launch template with the new S3 JAR path and trigger an Instance Refresh.
4.	Instance Refresh: ASG replaces instances with the new launch template configuration.
5.	Validation: Verify that the new instances are healthy and serving traffic before terminating old instances.


## Project Structure

```
com.craigwoodcock.fishingapp
├── config
├── controller
│   ├── apiController
│   └── webController
├── exception
├── model
│   ├── dto
│   ├── entity
│   └── id
├── repository
├── security
├── service
└── utils
```

## Key Components

### Web Interface

- User registration and authentication
- Dashboard with fishing history
- Session management interface
- Catch logging system

### REST API

- JWT-based authentication
- User management endpoints
- Session and catch management
- Mobile client support

## Getting Started

### Prerequisites

- Java 17
- MySQL
- Maven

### Installation

1. Clone the repository
2. Install MySQL Workbench
3. Configure MySQL Workbench and run `FishingApp.sql` file to create database.
4. Configure MySQL connection in `application.properties`:
    ```
     spring.datasource.url=jdbc:mysql://<ip or localhost>:3306/fishing_app
     spring.datasource.username=<MySQL DB Username>
     spring.datasource.password=<MySQL DB Password>
    ```
5. Build the project: `mvn clean install`
6. Run the application: `mvn spring-boot:run`

API Usage
Authentication API
1. Register a New User
To register a new user, send a POST request to /api/auth/register with the following JSON body:
    ```
    {

    "name": "Your Name",
    "username": "yourUsername",
    "email": "your.email@example.com",
    "password": "yourSecurePassword"
    }
    ```

Ensure the Content-Type header is set to application/json. If the request is successful, you will receive a confirmation response.

2. Login
Once registered, log in by sending a POST request to /api/auth/login with the following JSON body:
    ```
    {
    "username": "yourUsername",
    "password": "yourSecurePassword"
    }
    ```


    The response will contain a token in the following format:

        ```

            {
            "token": "yourJWTTokenHere"
            }

        ```

3. Access Secured Endpoints
Use the token from the login response to access secured endpoints. Add an Authorization header with the value Bearer {token} to your requests:
    ```
    Authorization: Bearer yourJWTTokenHere
    ```

Notes:
   - All API requests and responses use application/json.
   - Tokens may expire; re-login or refresh the token as needed.
   - Use a REST client like Postman or curl for testing API calls.

## Upcoming Features

- AWS S3 integration for photo storage
- Enhanced statistics dashboard
- Advanced search and filtering
- Session sharing capabilities
- Administrative features

## Security

- Spring Security implementation
- JWT authentication for API
- Role-based access control
- Session management

For more information about the frameworks and libraries used:

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html)
- [Tailwind Documentation](https://tailwindcss.com/docs)
- [Maven Documentation](https://maven.apache.org/guides/getting-started/index.html)

