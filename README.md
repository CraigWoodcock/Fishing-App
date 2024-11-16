- [Fishing Session Tracker 🎣](#fishing-session-tracker-)
  - [Features](#features)
  - [Tech Stack](#tech-stack)
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

A comprehensive Spring Boot application for tracking fishing sessions, managing catches, and analyzing fishing history. Features both a web interface and REST API support.

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
     - `spring.datasource.url=jdbc:mysql://<ip or localhost>:3306/fishing_app`
     - `spring.datasource.username=<MySQL DB Username>`
     - `spring.datasource.password=<MySQL DB Password>`
5. Build the project: `mvn clean install`
6. Run the application: `mvn spring-boot:run`

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

