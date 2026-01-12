# User Order Management Service

A Spring Boot REST API for managing users and their orders.

## Tech Stack
- Java
- Spring Boot
- MySQL
- Redis
- Apache Kafka
- Docker (for Redis and Kafka)
- Gradle

## Features
- User and Order management
- One-to-Many / Many-to-One relationship between User and Order
- Layered architecture (Controller → Service → Repository)
- DTO-based request/response handling
- Redis-based caching for frequently accessed data

## Event-Driven Architecture

Uses Kafka producers and consumers for user and order creation,
enabling event-driven processing. Includes full Docker-based Kafka setup
and configuration updates.


## How to Run

1. Clone the repository
2. Setup database and Redis Cache(see below)
3. Setup Kafka (see below)
4. Runn the app

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

## Kafka Setup (Local Development using Docker & KRaft)

### Big Picture

This project uses **Apache Kafka running in Docker** in **KRaft mode** (ZooKeeper-less) as a
**single-node local broker** for development.

### How Kafka Is Running in This Project

#### 1. Kafka is NOT installed locally

Kafka is **not installed on the host machine**.  
All Kafka components run inside Docker.

#### 2. Kafka Docker Image Used
confluentinc/cp-kafka:7.6.0

This image provides Kafka with built-in support for **KRaft mode**.

#### 3. Generate Kafka Cluster ID (KRaft requirement)

```bash
docker run --rm confluentinc/cp-kafka:7.6.0 kafka-storage random-uuid

#### 4. Start Kafka in Kraft Mode
docker run -d --name kafka \
  -p 9092:9092 \
  -e KAFKA_PROCESS_ROLES=broker,controller \
  -e KAFKA_NODE_ID=1 \
  -e CLUSTER_ID=REPLACE_WITH_YOUR_GENERATED_ID \
  -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@localhost:9093 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER \
  -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  confluentinc/cp-kafka:7.6.0

#### 5. Create topics (manually)
docker exec -it kafka kafka-topics \
  --bootstrap-server localhost:9092 \
  --create \
  --topic user_created \
  --partitions 1 \
  --replication-factor 1

docker exec -it kafka kafka-topics \
  --bootstrap-server localhost:9092 \
  --create \
  --topic order_created \
  --partitions 1 \
  --replication-factor 1

#### 6. Verify it works
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092

