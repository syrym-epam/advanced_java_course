🚀 Getting Started Guide & README
This guide covers how to set up, run, and test your Spring Boot microservices, Kafka, and PostgreSQL infrastructure using Docker Compose.
---
### Prerequisites
+ Docker & Docker Compose installed on your machine.
+ `curl` installed locally (usually pre-installed on Linux/macOS/Git Bash for Windows).


### Project Structure Overview
+ `product-service`: Microservice running on port `8080`, connected to PostgreSQL (`product-db`).
+ `resource-service`: Microservice running on port `8082`, consuming Avro messages from Kafka using Confluent Schema Registry.
+ `kafka`: Apache Kafka (KRaft mode) on port `9092` (external) / `29092` (internal).
+ `schema-registry`: Confluent Schema Registry on port `8081`.
### 1. Launch Instructions
Configure Environment Variables
Ensure your `.env` file or docker-compose environment variables are set up correctly:
```bash
Code snippet
POSTGRES_DB_PRODUCT=product-db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
PRODUCT_SERVICE_DATASOURCE_URL=jdbc:postgresql://product-db:5432/product-db
SPRING_PROFILES_ACTIVE=prod
PROD_ADMIN_USER=admin
PROD_ADMIN_PASSWORD=password
PROD_STANDARD_USER=user
PROD_STANDARD_USER_PASSWORD=user_password
KAFKA_SERVICE_URL=http://kafka:29092
SCHEMA_REGISTRY_URL=http://schema-registry:8081
```
### 2.Build and Start All Services
Run the following command from the root directory where your `docker-compose.yml` is located:
```Bash
docker compose up --build -d
```
### Verify Container Health
Check that all containers are up and healthy:
```Bash
docker compose ps
```

Sent request using Curl:
```Bash
curl -X POST http://localhost:8080/api/message/product \
  -H "Content-Type: application/json" \
  -d '{"name": "Wireless Mouse", "price": 1000}'
  ```

### 3. Monitoring Consumer Logs
To verify that your resource-service successfully receives and deserializes the Avro message, stream your container logs:
```Bash
docker compose logs -f resource-service
```
Once a message is published to the message topic matching this schema, you will see the expected output:
```Plaintext
Consumed Avro message successfully!
Product Name: Sample Product
Product Price: 99.99
```