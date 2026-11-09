# PRO3 Architecture Update – Part 3

This document updates the C4 views for the Part 3 Animal Registration REST service.

> Scope note: The supplied repository contains the Part 2 Spring Boot/gRPC implementation, PostgreSQL schema and sample data. It does not contain the original Part 1 diagrams. The C1/C2 views below therefore preserve the assignment's slaughterhouse context and the previously discussed goal of independent stations with indirect messaging, while distinguishing that target architecture from what is implemented in this repository today.

## C1 – System Context View

```mermaid
flowchart LR
    Staff["Station 1 staff<br/>Register arriving animals"]
    Cutting["Station 2 staff<br/>Cut and weigh parts"]
    Packing["Station 3 staff<br/>Pack products"]
    Recall["External recall user<br/>Search animal/product traceability"]

    System["Slaughterhouse Information System<br/>Animal registration, production tracking<br/>and product recall"]

    Staff -->|"Register animal through REST"| System
    Cutting -->|"Production / part information"| System
    Packing -->|"Product and tray information"| System
    Recall -->|"Traceability query"| System
```

The Part 3 change adds the animal-registration capability used by Station 1. The external recall user continues to use the traceability capability introduced in Part 2.

## C2 – Container View

```mermaid
flowchart TB
    S1["Station 1 client"]
    S2["Station 2 client"]
    S3["Station 3 client"]
    R["External recall client"]

    subgraph Backend["Slaughterhouse backend – current repository"]
      REST["Animal Registration REST API<br/>Spring MVC Controller"]
      GRPC["Traceability gRPC API<br/>AnimalGrpcService / ProductGrpcService"]
      APP["Application services<br/>AnimalRegistrationService<br/>AnimalService / ProductService"]
      REPO["JDBC repositories<br/>AnimalRegistrationRepository<br/>AnimalRepository / ProductRepository"]
    end

    DB[("PostgreSQL<br/>animal, product,<br/>product_animal")]
    MQ["Message broker<br/>Publish–Subscribe / RabbitMQ<br/>(architectural communication component)"]

    S1 -->|"HTTP/JSON"| REST
    S2 -->|"station workflow messages"| MQ
    S3 -->|"station workflow messages"| MQ
    R -->|"gRPC"| GRPC
    REST --> APP
    GRPC --> APP
    APP --> REPO
    REPO -->|"SQL"| DB
```

### Communication

- Station 1 uses synchronous HTTP/JSON requests to register an animal and read registration data.
- Part 2 traceability queries use gRPC.
- The publish-subscribe/message-broker communication remains the architectural approach for decoupling station workflows, so a temporary network failure between stations does not have to stop every station.
- The supplied repository does **not** currently contain RabbitMQ producer/consumer code. The broker in this view represents the wider assignment architecture, not a claim that RabbitMQ integration has already been implemented.
- In the current codebase, REST and gRPC are hosted by the same Spring Boot application and use the same PostgreSQL database. This is a practical implementation choice; it is not yet a separately deployed microservice.

## C3 – Component View: Animal Registration REST API

```mermaid
flowchart TB
    Client["Station 1 client / Postman"]
    Controller["AnimalRegistrationController<br/>REST endpoints"]
    Validation["CreateAnimalRequest<br/>Jakarta Bean Validation"]
    Service["AnimalRegistrationService<br/>Registration and query use cases"]
    Repository["AnimalRegistrationRepository<br/>JdbcTemplate SQL"]
    DB[("PostgreSQL animal table")]
    Errors["RestExceptionHandler<br/>400 / 404 / 409 responses"]

    Client -->|"POST /api/animals<br/>GET /api/animals/{registrationNumber}<br/>GET /api/animals?date=...<br/>GET /api/animals?origin=..."| Controller
    Controller --> Validation
    Controller --> Service
    Service --> Repository
    Repository --> DB
    Controller -.-> Errors
    Service -.-> Errors
```

### Component responsibilities

| Component | Responsibility |
|---|---|
| `AnimalRegistrationController` | Exposes REST endpoints and translates HTTP requests into service calls |
| `CreateAnimalRequest` | Carries registration input and validates required fields |
| `AnimalRegistrationService` | Implements registration and the three required lookup use cases |
| `AnimalRegistrationRepository` | Reads and writes animal registration data using `JdbcTemplate` |
| `AnimalRegistration` | Represents the complete animal registration returned by the API |
| `RestExceptionHandler` | Maps validation, missing-animal and duplicate-registration errors to HTTP responses |

### Database changes

The existing `animal.registration_number` primary key is retained. Part 3 adds:

- `arrival_date DATE`
- `weight NUMERIC(10, 2)`
- `origin VARCHAR(255)`

The existing `product_animal.registration_number` foreign key remains connected to `animal.registration_number`. This preserves the Part 2 animal-to-product traceability relationship.

### Implemented versus architectural target

| Capability | Status in supplied code |
|---|---|
| Animal registration REST API | Implemented in this Part 3 update |
| Read animal by registration number | Implemented |
| Read animals by arrival date | Implemented |
| Read animals by origin farm | Implemented |
| PostgreSQL persistence | Implemented using existing `JdbcTemplate` setup |
| Part 2 gRPC traceability | Existing code retained |
| RabbitMQ station messaging | Not implemented in this repository snapshot |
| Independently deployed station microservices | Not implemented in this repository snapshot |
