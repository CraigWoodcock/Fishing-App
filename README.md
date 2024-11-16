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
2. Configure MySQL connection in `application.properties`
3. Run the database migration scripts
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`

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

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

---

For more information about the frameworks and libraries used:
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/reference/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

