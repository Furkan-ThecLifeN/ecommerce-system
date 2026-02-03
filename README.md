# Enterprise-Grade Event-Driven Microservices E-Commerce

This project is a high-performance, scalable, and cloud-native e-commerce backend system built with **Java 21** and **Spring Boot 3.3**. It focuses on real-world production challenges like distributed transactions, eventual consistency, and observability.



## 🚀 Key Features & Implementation
- **Microservices Architecture:** Independently deployable services including Auth, User, Product, Order, and Payment.
- **Event-Driven Design:** Asynchronous communication using **Apache Kafka** to handle complex flows like order placement and payment processing.
- **Service Discovery & Routing:** Centralized management via **Netflix Eureka** and **Spring Cloud Gateway**.
- **Security:** Distributed JWT-based authentication with RSA Key Pair encryption.
- **Resilience:** Fault tolerance implemented via **Resilience4j** (Circuit Breaker, Retry).
- **Distributed Logging:** End-to-end request tracing using **Correlation IDs** (X-Request-Id) and MDC.

## 🛠 Technology Stack
- **Backend:** Java 21, Spring Boot 3.3, Spring Cloud, Spring Data JPA
- **Database:** MS SQL Server (MSSQL), Redis (Caching)
- **Messaging:** Apache Kafka
- **Observability:** Spring Actuator, Logback (JSON Formatter), ELK Stack Ready
- **DevOps:** Docker, Docker Compose



## 🏗 System Architecture
1. **API Gateway:** Single entry point, JWT validation, and rate limiting.
2. **Discovery Server:** Eureka-based service registration.
3. **Common Module:** Shared library for DTOs, custom exceptions, and global logging filters.
4. **Order & Payment Flow:** - `Order Service` publishes `OrderCreatedEvent`.
   - `Payment Service` consumes the event, processes payment, and triggers the next step.

## 🏁 How to Run
The infrastructure is fully dockerized. To start the databases, messaging brokers, and core services:

```bash
cd infrastructure
docker-compose up -d