# PRO3 Semester Assignment – Part 2 and Part 3

This project keeps the Part 2 gRPC animal/product traceability service and adds the Part 3 animal registration REST API.

## Technology

- Java 25
- Spring Boot 4.1.1
- PostgreSQL and Spring JDBC (`JdbcTemplate`)
- gRPC for Part 2
- Spring MVC REST API for Part 3
- JUnit 5 and Mockito tests

## Part 3 – Animal Registration REST API

Base URL: `http://localhost:8080/api/animals`

### Register an animal

`POST /api/animals`

JSON body:

```json
{
  "registrationNumber": 201,
  "date": "2026-11-05",
  "weight": 510.25,
  "origin": "Farm C"
}
```

Returns HTTP `201 Created`, a `Location` header, and the saved animal. Registration numbers must be unique; weight must be greater than zero; date, weight, and origin are required.

### Read one animal

`GET /api/animals/201`

### Read animals arriving on a date

`GET /api/animals?date=2026-11-05`

Date format: `YYYY-MM-DD`.

### Read animals from an origin farm

`GET /api/animals?origin=Farm%20C`

The origin comparison is case-insensitive.

Exactly one of `date` or `origin` must be supplied when listing animals.

### Error responses

- `400 Bad Request`: invalid request body or invalid/missing list filter.
- `404 Not Found`: registration number does not exist.
- `409 Conflict`: registration number already exists.

## Database

The existing `animal` table is extended with `arrival_date`, `weight`, and `origin`. The existing registration number primary key and `product_animal` foreign key are retained, so Part 2 traceability remains connected.

`schema.sql` creates or upgrades the tables. `data.sql` retains the Part 2 sample product relationships and provides sample registration details for animals 101–103.

Database settings are in `src/main/resources/application.properties`. Update the PostgreSQL username/password to match your local PostgreSQL setup.

## Run

```bash
./mvnw clean test
./mvnw spring-boot:run
```

The application requires PostgreSQL database `pro3` to be running and accessible with the configured credentials.

## Test

Run the JUnit tests:

```bash
./mvnw test
```

The REST endpoints can also be tested using Postman with the examples above.

## Part 2 – gRPC

The existing `.proto` file and gRPC services remain in place. See the original Part 2 examples below:

- Animal registration numbers: 101, 102, 103
- Product IDs: 1, 2, 3
