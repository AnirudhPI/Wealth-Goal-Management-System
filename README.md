# Wealth-Goal-Management-System

A **personal finance management** application that allows users to track incomes, expenses, investments, and savings goals. It leverages **GraphQL** for querying and mutating data, **Spring Boot** for rapid development, and **PostgreSQL** for reliable data storage.

---

## 1. Technical Stack

- **Java 17+**  
  Core language for the application.

- **Spring Boot (3.x)**
    - **Spring Data JPA** for database interactions.
    - **Spring Security** for authentication and authorization (optional, if configured).
    - **Spring GraphQL** for the GraphQL API.

- **GraphQL**
    - **Schema-First** approach defining `schema.graphqls`.
    - **DataLoader** for batching and caching user lookups.

- **PostgreSQL**
    - Relational database for storing users, incomes, expenses, investments, and savings goals.

- **Maven**
    - Project management and dependency resolution.

---

## 2. Features

1. **User Management**
    - Create and fetch user details.
    - Securely store passwords (BCrypt or JWT-based security, if implemented).

2. **Income Tracking**
    - Create and list incomes for each user.
    - Query incomes by user ID.

3. **Expense Tracking**
    - Create and list expenses for each user.
    - Paginated expenses (page, size, sorting).

4. **Investment Management**
    - Create and list investments for each user.
    - Update or delete investments.

5. **Savings Goals**
    - Create and list savings goals for each user.
    - Update or delete savings goals.

6. **Batch Loading**
    - Uses **GraphQL DataLoader** to batch-fetch `User` objects, reducing N+1 queries.

---

## 3. Advanced Features

1. **JWT-Based Authentication**
    - Issue and validate tokens for stateless security.

2. **Custom Exception Handling**
    - `UserNotFoundException`, etc.
    - Provides descriptive error messages in GraphQL responses.

3. **Pagination & Sorting**
    - Easily query paginated data (e.g., `getExpensesPaginated`) with sorting by fields.

4. **Asynchronous Resolvers**
    - Leverages **CompletableFuture** to handle user lookups and other async operations.

5. **Unit & Integration Tests**
    - Comprehensive tests for GraphQL queries/mutations, DataLoader usage, and security flows.

--- 

## 4. Project Set Up & Run

### A. Prerequisites

1. **Java 17+**  
   Make sure you have Java 17 or later installed.

2. **Maven**  
   For building the project.

3. **PostgreSQL**
    - Install and run a local PostgreSQL instance.
    - Create a database (e.g., `finance_app`) and a user with the necessary privileges.

### B. Clone the Repository

```bash
git clone https://github.com/YourUsername/Wealth-Goal-Management-System.git
cd Wealth-Goal-Management-System
```

### C. Configure Database

Open src/main/resources/application.properties and update:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_app
spring.datasource.username=finance
spring.datasource.password=finance

spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
```

### D. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### E. Testing the GraphQL Endpoint

By default, the GraphQL endpoint will be /graphql.
Download graphql-playground to test GraphQL endpoints:

```bash
brew install --cask graphql-playground
```

Interact and learn from GraphQL APIs.