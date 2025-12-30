# Coupon API

API para gerenciamento de cupons de desconto.

## Tecnologias

- Java 17, Spring Boot
- H2 (em memória)
- JPA / Hibernate
- Docker & Docker Compose
- Swagger (documentação)
- JUnit 5 + Mockito
- Jackson (LocalDateTime)

---

## Rodando localmente

```bash
mvn clean spring-boot:run
```

- A aplicação ficará em: `http://localhost:8080`
- H2 console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:coupondb`
  - User: `sa` / Password: (vazio)

---

## Rodando com Docker

```bash
docker-compose up --build
```

- Dockerfile:
```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

- docker-compose.yml:
```yaml
version: "3.8"
services:
  coupon-api:
    build: .
    ports:
      - "8080:8080"
```

---

## Testes

```bash
mvn clean test
```

- Cobertura >= 80% das regras de negócio
- Banco H2 em memória
- Testes unitários e de integração usando JUnit 5 + Mockito

---

## Regras de Negócio

- Cupons não podem ter data de expiração no passado
- Exclusão de cupom altera `status` para `DELETED`
- Lógica encapsulada no **objeto de domínio** `Coupon`

---

## Swagger

- Documentação disponível em: `http://localhost:8080/swagger-ui.html`
