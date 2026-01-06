# User Order Management Service

A Spring Boot REST API for managing users and their orders.

## Tech Stack
- Java
- Spring Boot
- MySQL
- Redis
- Docker (for Redis)
- Gradle

## Features
- User and Order management
- One-to-Many / Many-to-One relationship between User and Order
- Layered architecture (Controller → Service → Repository)
- DTO-based request/response handling
- Redis-based caching for frequently accessed data

## How to Run

1. Clone the repository
2. Setup database and Redis Cache(see below)
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

## Caching & Infrastructure

This project uses Redis for caching to improve performance and reduce
database load.

Redis is not installed directly on the machine — it is run using Docker
to keep the environment consistent and easy to set up across systems.

## Redis Setup (Using Docker)

Redis runs inside a Docker container on the local machine.

Start Redis with:

```bash
docker run -d --name redis-server -p 6379:6379 redis

Verify Redis is running using: docker ps

src/main/resources/application-example.properties contains this config for your redis setup:

spring.data.redis.host=localhost
spring.data.redis.port=6379

## Example APIs

GET  /api/users/{id}  
POST /api/users

