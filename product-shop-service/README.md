# Product Shop Service

## Overview

The Product Shop Service is a Spring Boot REST API developed as part of the Java Dev Camp project.

The service simulates a simplified financial services product onboarding platform where customers can:
- view products
- retrieve customer profiles
- submit product applications

The application follows a layered Spring Boot architecture using:
- Controllers
- Services
- Repositories
- PostgreSQL persistence

The project also includes:
- validation
- global exception handling
- structured logging
- actuator monitoring endpoints
- database seed data

---

# Technologies Used

- Java 17
- Spring Boot 3
- Spring Web (Spring MVC)
- Spring Data JPA
- PostgreSQL
- Hibernate
- Lombok
- Spring Validation
- Spring Boot Actuator
- Maven
- Docker / Docker Compose
- Bruno API Client

---

# Running the Application

## Prerequisites

Ensure the following are installed:

- Java 17
- Maven
- Docker Desktop
- Git
- IntelliJ IDEA

---

# Starting Dependencies

From the root directory:

```powershell
$env:PUB_KEY = Get-Content app.pub; docker compose up --build
```

This starts:
- PostgreSQL
- supporting dev camp services

---

# Running the Product Shop Service

Navigate to:

```text
product-shop-service
```

Run:

```powershell
mvn spring-boot:run
```

Or run the `ProductShopServiceApplication` class directly from IntelliJ.

Application runs on:

```text
http://localhost:8090
```

---

# Architecture

The application follows a layered architecture:

```text
Controller Layer
    ↓
Service Layer
    ↓
Repository Layer
    ↓
PostgreSQL Database
```

---

# Project Structure

```text
src/main/java
│
├── configuration
├── controller
├── dto
│   ├── request
│   └── response
├── exception
├── model
│   └── enums
├── repository
├── security
└── service
```

---

# API Endpoints

# Products

## Get All Products

```http
GET /products
```

## Get Product By ID

```http
GET /products/{id}
```

## Create Product

```http
POST /products
```

Sample Body:

```json
{
  "name": "Gold Exclusive Investment",
  "description": "Investment for premium customers",
  "price": 15000,
  "fulfilmentType": "B",
  "active": true,
  "qualifyingCustomerTypes": [
    "INDIVIDUAL"
  ],
  "qualifyingAccountTypes": [
    "GOLD_CHEQUE",
    "PLATINUM_CHEQUE"
  ]
}
```

---

# Customers

## Get All Customers

```http
GET /customers
```

## Get Customer By ID

```http
GET /customers/{id}
```

## Create Customer

```http
POST /customers
```

Sample Body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "idNumber": "9001015009087",
  "phoneNumber": "0821234567",
  "password": "Password123!",
  "customerType": "INDIVIDUAL",
  "accountTypes": [
    "GOLD_CHEQUE",
    "SAVINGS"
  ]
}
```

---

# Product Applications

## Get All Product Applications

```http
GET /product-applications
```

## Get Product Application By ID

```http
GET /product-applications/{id}
```

## Create Product Application

```http
POST /product-applications
```

Sample Body:

```json
{
  "status": "PENDING",
  "customerId": 1,
  "productId": 1
}
```

---

# Validation

The application uses Spring Validation annotations to validate incoming requests.

Examples:
- required fields
- email validation
- null checks

Example validation response:

```json
{
  "messages": {
    "email": "Email is required"
  },
  "error": "Validation Failed",
  "status": 400
}
```

---

# Exception Handling

A global exception handler is implemented using:

```java
@RestControllerAdvice
```

Handled exceptions include:
- Resource not found
- Validation failures

Example:

```json
{
  "error": "Not Found",
  "message": "Customer not found with id: 999",
  "status": 404
}
```

---

# Logging

Structured logging is implemented using SLF4J and Lombok `@Slf4j`.

Examples include:
- fetching entities
- creating entities
- warning logs for missing resources

Example:

```text
Fetching all products
Creating customer with email: john.doe@email.com
Product not found with id: 999
```

---

# Actuator Endpoints

Spring Boot Actuator is enabled for monitoring and health checks.

## Health

```http
GET /actuator/health
```

## Info

```http
GET /actuator/info
```

## Metrics

```http
GET /actuator/metrics
```

---

# Seed Data

The application automatically seeds:
- initial products
- initial customers

on startup if the database is empty.

---

# Database

The application uses PostgreSQL with Spring Data JPA and Hibernate.

Hibernate automatically:
- creates tables
- updates schema changes
- maps entities to database tables

---

# DTO Layer

The service uses DTOs to separate the external API contract from internal JPA entities.

Incoming requests are handled through request DTOs, such as:

- `CreateProductRequest`
- `CreateCustomerRequest`
- `CreateProductApplicationRequest`

API responses are returned through response DTOs, such as:

- `ProductResponse`
- `CustomerResponse`
- `ProductApplicationResponse`

This prevents controllers from exposing database entities directly and keeps the API contract cleaner and safer.

# Bruno Collection

The Bruno API collection includes:
- happy path requests
- validation tests
- exception tests
- actuator endpoint tests

Collection structure:

```text
Products
Customers
Product Applications
Actuators
```

---

# Authentication & Security

The application uses JWT-based authentication with Spring Security.

## Features

* BCrypt password hashing
* JWT access tokens
* JWT refresh tokens
* Stateless authentication
* Role-based authorization
* Protected endpoints
* Custom unauthorized responses

## Roles

The application currently supports:

* CUSTOMER
* ADMIN

### Authorization Rules

| Endpoint                   | Access        |
| -------------------------- | ------------- |
| GET /products              | Public        |
| POST /products             | ADMIN only    |
| POST /customers            | Authenticated |
| POST /product-applications | Authenticated |
| GET /profile/me            | Authenticated |

## Login

```http
POST /auth/login
```

Example request:

```json
{
  "email": "admin@productshop.com",
  "password": "Admin123!"
}
```

Example response:

```json
{
  "accessToken": "<jwt>",
  "refreshToken": "<refresh-token>",
  "tokenType": "Bearer"
}
```

## Refresh Token

```http
POST /auth/refresh
```

Example request:

```json
{
  "refreshToken": "<refresh-token>"
}
```

# Swagger / OpenAPI

Swagger UI is enabled for API exploration and testing.

Access Swagger UI at:

```text
http://localhost:8090/swagger-ui/index.html
```

Secured endpoints can be tested by:

1. Calling `/auth/login`
2. Copying the returned JWT access token
3. Clicking the `Authorize` button in Swagger
4. Pasting the JWT token

# Product Eligibility

The application supports product eligibility checks aligned with the BRS.

Eligibility is determined using:

* customer type
* owned account types
* product qualifying customer types
* product qualifying account types

## Eligibility Endpoint

```http
GET /product-applications/eligibility
```

Example:

```http
GET /product-applications/eligibility?customerId=1&productId=1
```

Example failure response:

```json
{
  "eligible": false,
  "reason": "Customer does not qualify for this product",
  "failedChecks": [
    "Customer requires one of these accounts: [SIGNET_CHEQUE]"
  ]
}
```


