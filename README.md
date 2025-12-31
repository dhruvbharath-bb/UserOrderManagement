# User Order Management Service

A Spring Boot REST API for managing users and their orders.

## Tech Stack
- Java
- Spring Boot
- MySQL
- Gradle

## Features
- User and Order management
- One-to-Many / Many-to-One relationship between User and Order
- Layered architecture (Controller → Service → Repository)
- DTO-based request/response handling

## How to Run

1. Clone the repository
2. Setup database (see below)
3. Run the app

./gradlew bootRun

Server runs at:
http://localhost:8080

## Database Setup

This project uses MySQL.

Create `application.properties` by copying:

src/main/resources/application-example.properties

Then update:

spring.datasource.url  
spring.datasource.username  
spring.datasource.password

## Example APIs

GET  /api/users/{id}  
POST /api/users

