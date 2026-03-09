# Fenix Commerce Platform - Multi-Tenant Ingestion API

A robust, multi-tenant commerce ingestion API built with **Java 17** and **Spring Boot 3**. 
Designed for organizations that operate multiple eCommerce websites, this centralized platform handles order ingestion, fulfillment management, and real-time shipment tracking with idempotency and upsert capabilities.

---

## Key Features

- **Multi-Tenancy Hierarchy:** Follows a strict `Company (Organization) → Website → Order` hierarchy.
- **Idempotent Operations:** Upsert logic for orders and tracking events to prevent duplicate records on webhook retries.
- **Data Integrity:** Utilizes UUIDs for primary keys to ensure security and uniqueness across distributed components, which is best practice in modern microservices.
- **Validation & Error Handling:** Global exception handling via `@ControllerAdvice` combining generic responses with field-level validation messages.
- **Pagination & Sorting:** Built-in endpoint support for querying large datasets utilizing Spring Data JPA's native pagination capability.
- **Interactive API Documentation:** Auto-generated Swagger UI integration for interactive API exploration.

---

## Technology Stack

| Component | Technology |
|---|---|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.3.5 |
| **Database** | MySQL 8 |
| **ORM** | Spring Data JPA + Hibernate |
| **Build Tool** | Maven Wrapper (`mvnw`) |
| **Utilities** | Lombok, Jackson, SpringDoc OpenAPI |

---

## Entity Architecture (Data Model)

The application models a highly relational structure tailored for B2B2C pipelines:
1. **Company (Organization):** The top-level multi-tenant container for an entire business.
2. **Website:** E-commerce store domains and sub-businesses belonging to a specific company.
3. **Order:** Purchases (ingested via webhook/API) made on a website. Tracks financial status and fulfillment lifecycle.
4. **Fulfillment:** Shipping and logistics records created against specific orders. 
5. **Tracking:** Granular, chronological shipping events (e.g., "In Transit", "Delivered") associated with a fulfillment.

---

## Getting Started

### Prerequisites
- **Java 17+** Installed
- **MySQL 8+** Client/Server Installed
- **Maven 3.6+** (Optional, project includes Maven Wrapper)

### Setup & Run Instructions

**1. Database Initialization**
Log in to your MySQL instance and execute the following queries:
```sql
CREATE DATABASE fenix_platform;
```

**2. Configure Application Properties**
Update `src/main/resources/application.properties` with your database credentials. Ensure Spring's DDL auto-generation creates tables appropriately.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fenix_platform?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**3. Build and Run the Application**
Navigate to the project root and execute the Maven Wrapper:
```bash
# Clean and Verify (Runs unit and integration tests)
./mvnw clean verify

# Run the Spring Boot application locally
./mvnw spring-boot:run
```
The server bootup process will automatically validate migrations and start HTTP endpoints on `http://localhost:8080`.

**4. Explore the API Interactively**
Open your favorite browser to review the complete API specification at:
Swagger UI → `http://localhost:8080/swagger-ui.html`

---

## Main API Reference

*(All successful JSON payloads return corresponding Data Transfer Objects preventing DB internal leakage).*

### Organizations API (`/organizations`)
- `POST /organizations` - Create a new organization.
- `GET /organizations` - List all organizations.
- `GET /organizations/{id}` - Get organization details (UUID based).
- `GET /organizations/{id}/orders` - Get a paginated stream of all orders under an organization's umbrella.

### Websites API (`/websites`)
- `POST /websites?companyId={id}` - Register an active website associated with an organization.
- `GET /websites` - Enumerate all websites across all tenants.

### Orders API (`/orders`)
- `POST /orders?websiteId={id}` - **Idempotent Ingestion Endpoint:** Safely create an order for a website. Upserts data based on `externalOrderId` checks.
- `GET /orders` - View all orders (Supports page/size query params).
- `GET /orders/company/{id}` - Fetch localized orders strictly under a parent organization ID.
- `GET /orders/search?companyId=&startDate=&endDate=` - Perform granular date range isolation.

### Fulfillments API (`/fulfillments`)
- `POST /fulfillments/{orderId}` - Add a fulfillment strategy/provider against a parent order ID.
- `GET /fulfillments/order/{orderId}` - View all grouped fulfillments under an order.

### Tracking API (`/tracking`)
- `POST /tracking/{fulfillmentId}/events` - Continuously ingest new event statuses for a specific shipment package.
- `GET /tracking/fulfillment/{fulfillmentId}` - Fetch tracking history in chronological sequence.

---

## Testing Strategy

The repository contains end-to-end multi-layer tests focusing heavily on transactional integrity.
- **`ApiIntegrationTest`**: Boots a segregated Spring application context to assert full path logic testing: *Org Creation → Website Binding → Order Ingestion → Fulfillment Attachment → Tracking Ping*.
- Validation triggers test specific constraint annotation checking directly mimicking HTTP 400 Bad Requests.

Run via the terminal using:
```bash
./mvnw test
```

---

## Source Package Structure

```text
src/main/java/com/fenix/platform/
├── controller/     # REST layer mapping and HTTP response wrapping
├── service/        # Core business/transactional logic, upserts, and rules
├── repository/     # Declarative Spring Data JPA database interfaces
├── entity/         # Hibernate JPA strict domain models
├── dto/            # Immutable Request & Response payloads for secure API contracts
├── mapper/         # Bi-directional transformations mapping logic (Entity ↔ DTO)
└── exception/      # Centralized error handling and unhandled RuntimeException intercepts
```

---

## Error Handling Format

Standardized exception mappings yield highly readable and actionable diagnostic payloads to API clients:

**404 Not Found (e.g., Target ID Missing)**
```json
{
  "timestamp": "2026-03-11T10:30:15",
  "status": 404,
  "error": "Not Found",
  "message": "Website not found with id: dccdb6fc-65e9-4e08-9da3-0b0bbff2c0b7",
  "path": "/orders"
}
```

**400 Bad Request (e.g., Malformed Request Validation)**
```json
{
  "amount": "Order amount magnitude must be greater than zero",
  "currency": "Currency code must be exactly 3 characters"
}
```

---

## Architecture Design Decisions
1. **Strictly UUIDs over Long**: Scaled up global ID generation using Java `UUID` structures within the JPA layer to mitigate enumeration security risks and ensure safe system merging globally.
2. **Idempotent Webhooks (Upserts)**: Webhook endpoints acting upon `/orders` seamlessly upsert duplicate data leveraging `externalOrderId` searches. Clients blindly ping the system, trusting the internal platform to update or insert elegantly.
3. **Safe Recursive JSON Loadouts**: Used dual-purpose `@JsonManagedReference` / `@JsonBackReference` bindings, augmented cleanly by separation into explicit `DTO` classes, preventing Hibernate lazy-loading traps or memory exhaustion during tree traversal serialization.
