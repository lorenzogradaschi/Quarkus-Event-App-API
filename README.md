# Quarkus-Event-App-API

Quarkus-Event-App-API is a backend project built with **Quarkus** and **Jakarta EE** that manages event data.  
It follows clean architecture, SOLID principles, and modern Java backend standards.

This project is intended as a learning and professional showcase of best practices in:

- Jakarta RESTful Web Services (Jakarta WS-RS)
- JPA with Hibernate ORM
- DTOs and DAO Patterns
- REST APIs and custom exception handling
- Clean Code, SOLID & OOP principles
- SQL Server integration
- Swagger (OpenAPI) documentation
- Quarkus-specific enhancements (CDI, Beans, Dev Mode, etc.)

```txt
DATABASE
--------
SQL Server (Configured to run locally)

SERVER PORT
-----------
Running on port: 5555

AUTHENTICATION (Planned)
------------------------
Auth with a custom entity provider (to be defined)

PROJECT PURPOSE
---------------
This API aims to demonstrate how to correctly use:

- Jakarta WS-RS (REST)
- Jakarta EE
- SOLID Principles
- JPA + Hibernate
- Native SQL Queries
- REST API Architecture
- Swagger/OpenAPI Annotations
- DTOs (Data Transfer Objects)
- DAO Pattern
- ObjectMappers (todo)
- Custom Exceptions
- Custom ExceptionMappers (todo)
- Criteria Queries (todo)
- Transactional Management
- CDI & Beans
- OOP Best Practices
```

## Getting Started

```txt
# Run the application in dev mode
./mvnw quarkus:dev
```

## Project Structure

```txt
src/main/java/it.eventmanager
├── dao                 # Data Access Objects
│   ├── EventDAO
│   └── UserDAO
├── dto                 # Data Transfer Objects
│   ├── EventDTO
│   └── UserDTO
├── entities            # JPA Entities
│   ├── Address
│   ├── Event
│   └── User
├── exception           # Custom Exceptions
│   ├── EventCustomException
│   └── UserCustomException
├── resource            # REST Resources
│   ├── EventResource
│   └── UserResource
├── restclient          # HTTP REST Clients
│   ├── EventClient.java
│   └── UserClient
├── utilmapper          # DTO ↔ Entity Mappers
│   ├── EventMapper
│   └── UserMapper
├── utilroles           # Enums and Roles
│   └── EventType
│   └── UserRole
```

Command to run it:

```txt
http://localhost:5555/swagger-ui
```



