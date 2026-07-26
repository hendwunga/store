<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring_Security-7.0.2-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/Thymeleaf-3.1.3-005A00?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License"/>
</p>

<h1 align="center">Store API</h1>

<p align="center">
  <strong>Full-stack product & category management application with REST API, role-based authentication, image upload, and OpenAPI documentation.</strong>
</p>

<p align="center">
  <a href="https://app.swaggerhub.com/hendwunga/store-api/1.1.1">
    <img src="https://img.shields.io/badge/SwaggerHub-API_Docs-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="SwaggerHub"/>
  </a>
  <a href="https://app.swaggerhub.com/hendwunga/store-api/1.1.1">
    <img src="https://img.shields.io/badge/Version-1.1.1-blue?style=for-the-badge" alt="Version"/>
  </a>
</p>

---

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [API Documentation](#api-documentation)
- [Quick Start](#quick-start)
- [Spring Profiles](#spring-profiles)
- [Environment Variables](#environment-variables)
- [Project Structure](#project-structure)
- [Deployment](#deployment)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

**Store** is a full-stack web application built with Spring Boot 4 for managing products and categories. It provides a Thymeleaf-based server-rendered UI and a RESTful JSON API, both secured with role-based authentication.

### Key Highlights

- **Dual Interface** — Thymeleaf UI for browser users + REST API for programmatic access
- **Role-Based Access Control** — `ADMIN` (full CRUD) and `USER` (read-only products)
- **Multi-Profile Architecture** — Seamless switching between local, dev, staging, and production environments
- **Cloud-Native Database** — Aiven MySQL for staging & production
- **Image Storage** — Smart fallback: Cloudinary (cloud) → Local filesystem
- **OpenAPI 3.0** — Full Swagger documentation hosted on SwaggerHub with live "Try it out"
- **Docker Ready** — Multi-stage Dockerfile + Docker Compose for local MySQL

### Live Demo

| Environment | URL | Status |
|-------------|-----|--------|
| SwaggerHub | [hendwunga/store-api](https://app.swaggerhub.com/apis/hendwunga/store-api/1.1.1) | Active |
| Local | `http://localhost:8080` | Requires local run |

---

## Architecture

```
┌──────────────┐     ┌──────────────────┐     ┌──────────────┐
│   Browser    │────▶│  Spring Boot App  │────▶│  Aiven MySQL │
│  (Thymeleaf) │     │  (Port 8080)      │     │  (Database)  │
└──────────────┘     └──────────────────┘     └──────────────┘
                            │                         ▲
┌──────────────┐            │                         │
│  SwaggerHub  │────────────┘                         │
│  (Try it out)│                                      │
└──────────────┘            ┌──────────────────┐      │
                            │  Cloudflare      │      │
┌──────────────┐            │  Tunnel          │      │
│  REST Client │───────────▶│  (Public URL)    │──────┘
│  / Mobile    │            └──────────────────┘
└──────────────┘
```

---

## Tech Stack

| Layer | Technology | Version | Purpose |
|-------|-----------|---------|---------|
| **Language** | Java | 17 | Runtime |
| **Framework** | Spring Boot | 4.0.1 | Application framework |
| **Security** | Spring Security | 7.0.2 | Authentication & authorization |
| **Template** | Thymeleaf | 3.1.3 | Server-side rendering |
| **ORM** | Hibernate (Spring Data JPA) | 7.2.0 | Database access |
| **API Docs** | SpringDoc OpenAPI | 2.8.4 | OpenAPI 3.0 specification |
| **Database** | MySQL / H2 | 8.0+ / 2.4 | Production / Development |
| **Image Upload** | Cloudinary + Local | 1.33.0 | Cloud & fallback storage |
| **Env Config** | spring-dotenv | 4.0.0 | `.env` file loading |
| **Container** | Docker | Multi-stage | Build & runtime |
| **CI/CD** | GitHub Actions | - | Automated pipeline |

---

## Features

### Core

| Feature | Description |
|---------|-------------|
| **Product CRUD** | Create, read, update, delete products with name, brand, price, description, and image |
| **Category CRUD** | Manage product categories (ADMIN only) |
| **Search** | Real-time search by product name or brand (case-insensitive) |
| **Role-Based Auth** | `ADMIN` — full access; `USER` — read-only products |
| **Image Upload** | Upload product images via multipart form (Cloudinary → local fallback) |

### API & Documentation

| Feature | Description |
|---------|-------------|
| **REST API** | 10 JSON endpoints under `/api/*` |
| **Swagger UI** | Interactive docs at `/swagger-ui/index.html` |
| **OpenAPI Spec** | `openapi.yaml` for SwaggerHub import |
| **Unified Response** | Consistent `{success, message, data}` envelope |

### Infrastructure

| Feature | Description |
|---------|-------------|
| **Multi-Profile** | `local`, `dev`, `staging`, `production`, `test` |
| **Spring Profiles** | YAML-based config per environment |
| **Docker** | Multi-stage build (Maven build + JRE runtime) |
| **Docker Compose** | Full stack with MySQL 8.0 |
| **Render Deploy** | `render.yaml` blueprint for one-click deploy |
| **Cloudflare Tunnel** | Expose local app to public internet for testing |
| **Actuator** | Health check at `/actuator/health` |

---

## API Documentation

> **[SwaggerHub Documentation Portal](https://app.swaggerhub.com/apis/hendwunga/store-api/1.1.1)**

### Endpoints

#### Products

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/api/products` | List all products (supports `?search=`) | — |
| `GET` | `/api/products/{id}` | Get product by ID | — |
| `POST` | `/api/products` | Create new product | `ProductRequest` |
| `PUT` | `/api/products/{id}` | Update product | `ProductRequest` |
| `DELETE` | `/api/products/{id}` | Delete product | — |

#### Categories

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/api/categories` | List all categories | — |
| `GET` | `/api/categories/{id}` | Get category by ID | — |
| `POST` | `/api/categories` | Create new category | `CategoryRequest` |
| `PUT` | `/api/categories/{id}` | Update category | `CategoryRequest` |
| `DELETE` | `/api/categories/{id}` | Delete category | — |

### Response Format

All endpoints return a unified JSON envelope:

```json
{
  "success": true,
  "message": "Products retrieved successfully",
  "data": [...]
}
```

### Request Schemas

<details>
<summary><strong>ProductRequest</strong></summary>

```json
{
  "name": "MacBook Pro 14 inch",
  "brand": "Apple",
  "categoryId": 1,
  "price": 19999000,
  "description": "MacBook Pro 14 inch with M3 Pro chip for maximum performance",
  "imageUrl": "https://example.com/macbook.jpg"
}
```

| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| `name` | string | Yes | minLength: 1 |
| `brand` | string | Yes | minLength: 1 |
| `categoryId` | integer | Yes | min: 1 |
| `price` | number | Yes | min: 0 |
| `description` | string | Yes | minLength: 10, maxLength: 2000 |
| `imageUrl` | string | No | Valid URI |

</details>

<details>
<summary><strong>CategoryRequest</strong></summary>

```json
{
  "name": "Elektronik"
}
```

| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| `name` | string | Yes | minLength: 1, must be unique |

</details>

### Example Requests

```bash
# List all products
curl http://localhost:8080/api/products

# Search products
curl http://localhost:8080/api/products?search=macbook

# Get single product
curl http://localhost:8080/api/products/1

# Create product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MacBook Pro 14 inch",
    "brand": "Apple",
    "categoryId": 1,
    "price": 19999000,
    "description": "MacBook Pro 14 inch with M3 Pro chip for maximum performance"
  }'

# Create category
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name": "Elektronik"}'

# Delete product
curl -X DELETE http://localhost:8080/api/products/1
```

---

## Quick Start

### Prerequisites

- **Java 17** or newer
- **Maven 3.8+** (or use the included Maven Wrapper `./mvnw`)
- **MySQL 8.0** (optional — H2 is used for local development)

### 1. Clone Repository

```bash
git clone https://github.com/hendwunga/store.git
cd store
```

### 2. Run (Local — H2, no MySQL required)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Open **http://localhost:8080**

### 3. Login Credentials

| Username | Password | Role | Access |
|----------|----------|------|--------|
| `admin` | `admin123` | ADMIN | Full CRUD (products & categories) |
| `user` | `user123` | USER | Read-only (products only) |

### 4. Access API Docs

| URL | Description |
|-----|-------------|
| `http://localhost:8080/swagger-ui/index.html` | Swagger UI (interactive) |
| `http://localhost:8080/v3/api-docs` | OpenAPI JSON spec |
| `http://localhost:8080/actuator/health` | Health check |

### 5. Run with Staging Database (Aiven MySQL)

```bash
export STAGING_DB_PASSWORD=<your-actual-password>
./mvnw spring-boot:run -Dspring-boot.run.profiles=staging
```

---

## Spring Profiles

| Profile | Database | Use Case | Notes |
|---------|----------|----------|-------|
| `local` | H2 (file-based) | Development without MySQL | No setup needed |
| `dev` | MySQL localhost | Development with MySQL | Requires local MySQL |
| `staging` | Aiven MySQL (cloud) | Testing with real DB | Requires env var `STAGING_DB_PASSWORD` |
| `production` | Aiven MySQL (cloud) | Deployment | Requires env var `STAGING_DB_PASSWORD` + `PORT` |
| `test` | H2 (in-memory) | Automated testing | Used by `mvn test` |

### Running with a Profile

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=<profile>
```

---

## Environment Variables

Copy `.env.example` to `.env` and fill in your values:

```bash
cp .env.example .env
```

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `PORT` | No | `8080` | Application server port |
| `STAGING_DB_PASSWORD` | For staging | — | Aiven MySQL staging password |
| `DEV_DB_URL` | For dev | — | Local MySQL JDBC URL |
| `DEV_DB_USER` | For dev | `root` | Local MySQL username |
| `DEV_DB_PASSWORD` | For dev | — | Local MySQL password |
| `CLOUDINARY_CLOUD_NAME` | No | — | Cloudinary cloud name |
| `CLOUDINARY_API_KEY` | No | — | Cloudinary API key |
| `CLOUDINARY_API_SECRET` | No | — | Cloudinary API secret |

> **Note:** Cloudinary is optional. When unavailable, image uploads automatically fall back to local filesystem storage (`uploads/` directory).

---

## Project Structure

```
store/
├── pom.xml                              # Maven build configuration
├── Dockerfile                           # Multi-stage Docker build
├── docker-compose.yml                   # Full stack with MySQL 8.0
├── render.yaml                          # Render.com deployment blueprint
├── openapi.yaml                         # OpenAPI 3.0 spec (SwaggerHub)
├── Procfile                             # Heroku/Railway deployment
├── .env.example                         # Environment variable template
│
├── src/main/java/com/hend/store/
│   ├── StoreApplication.java            # Application entry point
│   │
│   ├── config/
│   │   ├── CloudinaryConfig.java        # Cloudinary bean configuration
│   │   ├── DataInitializer.java         # Seeds default users & categories
│   │   ├── OpenApiConfig.java           # SpringDoc OpenAPI configuration
│   │   ├── SecurityConfig.java          # Spring Security rules & access control
│   │   └── WebConfig.java               # Static resource handler for /uploads
│   │
│   ├── controller/
│   │   ├── HomeController.java          # GET / → redirect to /products
│   │   ├── LoginController.java         # Login page
│   │   ├── ProductsController.java      # MVC CRUD for products
│   │   ├── CategoryController.java      # MVC CRUD for categories
│   │   ├── ProductRestController.java   # REST API for products
│   │   └── CategoryRestController.java  # REST API for categories
│   │
│   ├── dto/
│   │   ├── ApiResponse.java             # Generic {success, message, data} wrapper
│   │   ├── ProductRequest.java          # Product request body DTO
│   │   └── CategoryRequest.java         # Category request body DTO
│   │
│   ├── models/
│   │   ├── Product.java                 # JPA entity — products table
│   │   ├── Category.java                # JPA entity — categories table
│   │   └── ProductDTO.java              # Thymeleaf form-backing DTO
│   │
│   └── services/
│       ├── ProductsRepository.java      # Spring Data JPA — product queries
│       ├── CategoryRepository.java      # Spring Data JPA — category queries
│       ├── CloudinaryService.java       # Cloudinary image upload service
│       ├── ImageStorageService.java     # Smart router: Cloudinary → local
│       └── LocalStorageService.java     # Local filesystem image storage
│
├── src/main/resources/
│   ├── application.yml                  # Main config (active profile selection)
│   ├── application-local.yml            # H2 in-memory database
│   ├── application-dev.yml              # Local MySQL database
│   ├── application-staging.yml          # Aiven MySQL (staging)
│   ├── application-production.yml       # Aiven MySQL (production/Render)
│   ├── data-staging.sql                 # Staging seed data (6 categories, 10 products)
│   │
│   ├── static/
│   │   ├── index.html                   # Landing page
│   │   ├── css/styles.css               # Modern design system
│   │   └── js/app.js                    # Shared JavaScript utilities
│   │
│   └── templates/
│       ├── login.html                   # Glassmorphism login page
│       ├── products/
│       │   ├── index.html               # Product card grid
│       │   ├── CreateProduct.html       # Create form with image upload
│       │   └── EditProduct.html         # Edit form with image preview
│       ├── categories/
│       │   ├── index.html               # Category table
│       │   ├── CreateCategory.html      # Create form
│       │   └── EditCategory.html        # Edit form
│       └── error/
│           ├── 403.html                 # Forbidden
│           ├── 404.html                 # Not Found
│           └── error.html               # Generic error
│
└── src/test/java/com/hend/store/
    ├── StoreApplicationTests.java       # Context load test
    └── services/
        └── ProductsRepositoryTest.java  # CRUD & search repository tests
```

---

## Deployment

### Docker Compose (Local Development with MySQL)

```bash
docker compose up -d
```

| Service | Port | Description |
|---------|------|-------------|
| `app` | `8080` | Spring Boot application |
| `mysql` | `3306` | MySQL 8.0 database |

### Render.com (Production)

1. Connect GitHub repository `hendwunga/store`
2. Render auto-detects `render.yaml` blueprint
3. Set environment variable: `STAGING_DB_PASSWORD`
4. Deploy — Render builds and runs automatically

### Cloudflare Tunnel (Public Testing)

```bash
# Terminal 1: Start app
STAGING_DB_PASSWORD=<password> ./mvnw spring-boot:run -Dspring-boot.run.profiles=staging

# Terminal 2: Start tunnel
cloudflared tunnel --url http://localhost:8080
```

Use the generated `https://*.trycloudflare.com` URL for public access.

### Docker Hub (via GitHub Actions)

Push to `main` triggers CI/CD:

1. **Build & Test** — `mvn verify`
2. **Docker Build** — Multi-stage image
3. **Push** — Image to Docker Hub

**Required GitHub Secrets:**

| Secret | Description |
|--------|-------------|
| `DOCKER_USERNAME` | Docker Hub username |
| `DOCKER_PASSWORD` | Docker Hub access token |

---

## Testing

```bash
# Run all tests
./mvnw verify

# Run specific test
./mvnw test -Dtest=ProductsRepositoryTest

# Run with test profile (H2 in-memory)
./mvnw test -Dspring.profiles.active=test
```

**Test Coverage:**

| Test Class | Type | What It Tests |
|-----------|------|---------------|
| `StoreApplicationTests` | Context Load | Application context starts successfully |
| `ProductsRepositoryTest` | Repository | CRUD operations, search queries, category filtering |

---

## Contributing

1. Fork this repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m 'feat: add your feature'`
4. Push to branch: `git push origin feature/your-feature`
5. Open a Pull Request

### Commit Convention

This project follows [Conventional Commits](https://www.conventionalcommits.org/):

| Prefix | Purpose |
|--------|---------|
| `feat:` | New feature |
| `fix:` | Bug fix |
| `docs:` | Documentation changes |
| `style:` | CSS/UI changes |
| `refactor:` | Code restructuring |
| `test:` | Adding/updating tests |
| `chore:` | Build, config, tooling |
| `deploy:` | Deployment configuration |

---

## License

Distributed under the MIT License. See [`LICENSE`](LICENSE) for details.

---

<p align="center">
  Built with Spring Boot 4 + Thymeleaf + REST API + OpenAPI 3.0
</p>
