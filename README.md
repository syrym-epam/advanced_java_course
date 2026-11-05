# Spring Boot Product Tutorial & Exercise API

A production-ready RESTful API built with Spring Boot, Spring Security (OAuth2 Resource Server / JWT), Spring Data JPA, Flyway, and Actuator metrics monitoring.

---

## 🚀 Features & Architecture Highlights

- **Robust Domain Models**: Uses `BigDecimal` for precise financial data representation instead of primitive floating-point types (`Double`).
- **Input Validation**: Leverages Jakarta Validation (`@Valid`, `@NotNull`, `@NotBlank`, `@DecimalMin`) via dedicated Request DTOs (`ProductRequestDto`).
- **Secure Authentication & Authorization**:
  - Stateless JWT-based authentication via a custom `JwtTokenProvider` and Spring Security's `AuthenticationManager`.
  - BCrypt password encoding.
  - Method-level and path-level security using `@PreAuthorize` (e.g., `hasRole('ADMIN')` vs `hasRole('USER')`).
- **Database Migrations**: Managed via **Flyway**, featuring ANSI-compatible SQL scripts supporting both H2 and PostgreSQL.
- **Multi-Profile Configuration**: Configured with dedicated `dev` (H2), `prod` (PostgreSQL), and `stage, local` profiles.
- **Monitoring & Observability**: Integrated with Spring Boot Actuator and Micrometer custom counters (`exercise.products.creations` and `exercise.products.deletions`) for Prometheus scraping.

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security & OAuth2 Resource Server**
- **Spring Data JPA**
- **Flyway Migration**
- **H2 Database** (Development / Testing) & **PostgreSQL** (Production)
- **Lombok**
- **JUnit 5, Mockito & MockMvc** for testing

---

## 📂 Project Structure

```text
src/
├── main/
│   ├── java/com/tutorial/exercise/
│   │   ├── components/      # JwtTokenProvider, ProductMetrics
│   │   ├── config/          # SecurityConfig, UserDetailsConfig, SecurityUsersProperties
│   │   ├── controllers/     # ProductController, AuthController
│   │   ├── dto/             # ProductRequestDto
│   │   ├── entities/        # Product
│   │   ├── repositories/    # ProductRepository
│   │   └── services/        # ProductService
│   └── resources/
│       ├── db/migration/    # Flyway SQL scripts (V1__init_product_table.sql)
│       ├── application.yaml
│       ├── application-dev.yaml
│       └── application-prod.yaml
│       └── application-stage.yaml
│       └── application-local.yaml
└── test/
    └── resources/
        └── ...files for test