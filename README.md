# Sistema Bancário Backend

Alunos: Juliano Kenzo Watanabe Santana, Carlos Andre Scherer

https://github.com/Capicheee/Sistema-Banc-rio.git

Este projeto é uma aplicação backend para um sistema bancário desenvolvido em Spring Boot. O sistema permite a gestão de contas bancárias, clientes e transações.

O frontend está na master branch, com sua respectiva documentação.

## Funcionalidades

- Gerenciamento de Contas: Criar, atualizar, deletar e consultar contas bancárias.
- Gerenciamento de Clientes: Criar, atualizar, deletar e consultar de clientes.
- Gerenciamento de Transações: Realizar e consulta de transações entre contas.
- Gerenciamento de Investimentos: Criar, resgatar e consultar investimentos.
- Gerenciamento de Empréstimos: Contratar empréstimos com juros, pagar parcelas.
- PIX: Gerar PIX com QR Code e código automático, pagar pix com o código.

## Tecnologias utilizadas

- Java 11
- Spring Boot 2.7.x
- Spring Web (REST)
- Spring Data JPA (Hibernate)
- HikariCP (pool de conexões padrão)
- Springdoc OpenAPI (Swagger UI) — documentação da API
- H2 Database
- ZXing (Google) - (Pix)
- JUnit 5 + Mockito
- Maven (mvn / mvnw)

## Dependências Principais

xml - Spring Boot Starter Web
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

Spring Boot Starter Data JPA
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

H2 Database
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

Springdoc OpenAPI (Swagger)
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.14</version>
</dependency>

ZXing QR Code Generator
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.5.1</version>
</dependency>
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>javase</artifactId>
    <version>3.5.1</version>
</dependency>

JUnit 5:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

## URLs úteis

- Swagger UI: http://localhost:8080/swagger-ui/index.html

## Como executar o projeto (local / dev)

1. Clone o repositório:
git clone <repo-url>
cd sistema-bancario-backend

2. Build e testes:
mvn -U clean package

mvn test

(escolher entre 3 ou 4, preferível 3)

3. Executar com banco H2:
mvn -DskipTests spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

4. Gerar JAR e executar:
mvn -DskipTests package

java -jar target/sistema-bancario-backend-1.0-SNAPSHOT.jar --spring.profiles.active=dev

- Para testes unitários e mocks, usamos JUnit Jupiter e Mockito.
