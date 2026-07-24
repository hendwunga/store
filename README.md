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
  <strong>Aplikasi web manajemen produk & kategori dengan REST API, autentikasi role-based, upload gambar, dan dokumentasi OpenAPI/Swagger.</strong>
</p>

<p align="center">
  <a href="https://app.swaggerhub.com/apis/hendwunga/store-api/1.0.0">
    <img src="https://img.shields.io/badge/SwaggerHub-API_Documentation-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="SwaggerHub"/>
  </a>
  <a href="http://localhost:8080/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/Swagger_UI-Local-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger UI"/>
  </a>
</p>

---

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [API Documentation](#api-documentation)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Project Structure](#project-structure)
- [Deployment](#deployment)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

**Store** adalah aplikasi web full-stack yang dibangun dengan Spring Boot 4 untuk manajemen produk dan kategori. Aplikasi ini menyediakan:

- **Thymeleaf UI** — Interface web server-side rendered untuk pengguna biasa
- **REST API** — Endpoint JSON untuk integrasi dengan aplikasi lain / mobile
- **OpenAPI/Swagger** — Dokumentasi API interaktif yang bisa diakses publik

---

## Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Language** | Java | 17 |
| **Framework** | Spring Boot | 4.0.1 |
| **Security** | Spring Security | 7.0.2 |
| **Template** | Thymeleaf | 3.1.3 |
| **ORM** | Hibernate (Spring Data JPA) | 7.2.0 |
| **API Docs** | SpringDoc OpenAPI | 2.8.4 |
| **Database** | MySQL (prod) / H2 (dev & test) | 8.0+ / 2.4 |
| **Image Upload** | Cloudinary | 1.33.0 (optional) |
| **Container** | Docker (multi-stage build) | - |
| **CI/CD** | GitHub Actions | - |

---

## Features

### Core

- **Manajemen Produk** — CRUD produk lengkap (nama, brand, harga, deskripsi, gambar)
- **Manajemen Kategori** — CRUD kategori (hanya role ADMIN)
- **Search** — Pencarian produk berdasarkan nama atau brand (case-insensitive)
- **Autentikasi** — Login form dengan role-based access (ADMIN & USER)

### API & Documentation

- **REST API** — 10 endpoint JSON di bawah `/api/*`
- **Swagger UI** — UI interaktif di `/swagger-ui/index.html`
- **OpenAPI Spec** — File `openapi.yaml` untuk import ke SwaggerHub

### Infrastructure

- **Multi-profile** — `local`, `dev`, `test`, dan production
- **Docker** — Multi-stage build (Maven build + JRE runtime)
- **Docker Compose** — Full stack dengan MySQL 8.0
- **CI/CD** — GitHub Actions: test, build, push ke Docker Hub
- **Cloud Deployment** — Siap deploy ke Heroku / Railway (Procfile tersedia)
- **Cloudinary** — Upload gambar opsional (app tetap jalan tanpa Cloudinary)
- **Actuator** — Health check endpoint

---

## API Documentation

Dokumentasi API lengkap tersedia di SwaggerHub:

> **[🔗 SwaggerHub API Documentation Portal](https://app.swaggerhub.com/apis/hendwunga/store-api/1.0.0)**

### API Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/api/products` | List semua produk | Public |
| `GET` | `/api/products/{id}` | Detail produk | Public |
| `POST` | `/api/products` | Buat produk baru | Public |
| `PUT` | `/api/products/{id}` | Update produk | Public |
| `DELETE` | `/api/products/{id}` | Hapus produk | Public |
| `GET` | `/api/categories` | List semua kategori | Public |
| `GET` | `/api/categories/{id}` | Detail kategori | Public |
| `POST` | `/api/categories` | Buat kategori baru | Public |
| `PUT` | `/api/categories/{id}` | Update kategori | Public |
| `DELETE` | `/api/categories/{id}` | Hapus kategori | Public |

### Response Format

Semua endpoint mengembalikan response dalam format:

```json
{
  "success": true,
  "message": "Products retrieved successfully",
  "data": [...]
}
```

### Contoh Request

```bash
# List semua produk
curl http://localhost:8080/api/products

# Cari produk
curl http://localhost:8080/api/products?search=macbook

# Buat produk baru
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MacBook Pro 14 inch",
    "brand": "Apple",
    "categoryId": 1,
    "price": 19999000,
    "description": "MacBook Pro 14 inch dengan chip M3 Pro untuk performa maksimal"
  }'
```

---

## Quick Start

### Prerequisites

- **Java 17** atau lebih baru
- **Maven 3.8+** (atau gunakan Maven Wrapper `./mvnw`)
- **MySQL 8.0** (opsional, bisa pakai H2)

### 1. Clone Repository

```bash
git clone https://github.com/hendwunga/AntiGravity.git
cd AntiGravity/store
```

### 2. Jalankan (Local — H2, tanpa MySQL)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Buka **http://localhost:8080**

### 3. Login

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | ADMIN (akses semua fitur) |
| `user` | `user123` | USER (hanya produk) |

### 4. Akses API Docs

- **Swagger UI (lokal):** http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

---

## Configuration

### Environment Variables

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `MYSQL_URL` | No | MySQL Aiven cloud | JDBC connection URL |
| `MYSQL_USER` | Yes | - | MySQL username |
| `MYSQL_PASSWORD` | Yes | - | MySQL password |
| `PORT` | No | `8080` | Application port |
| `CLOUDINARY_CLOUD_NAME` | No | - | Cloudinary cloud name |
| `CLOUDINARY_API_KEY` | No | - | Cloudinary API key |
| `CLOUDINARY_API_SECRET` | No | - | Cloudinary API secret |

### Profiles

| Profile | Database | Use Case |
|---------|----------|----------|
| `local` | H2 file-based | Development tanpa MySQL |
| `dev` | MySQL localhost | Development dengan MySQL |
| `test` | H2 in-memory | Otomatis saat `mvn test` |
| *(default)* | MySQL via env vars | Production |

### Copy `.env` Template

```bash
cp .env .env.local
# Edit .env.local dengan kredensial kamu
```

---

## Project Structure

```
store/
├── pom.xml                          # Maven configuration
├── Dockerfile                       # Multi-stage Docker build
├── docker-compose.yml               # MySQL + App orchestration
├── openapi.yaml                     # OpenAPI spec untuk SwaggerHub
├── Procfile                         # Heroku/Railway deployment
├── .env                             # Environment variables template
│
├── src/main/java/com/hend/store/
│   ├── StoreApplication.java        # Entry point
│   │
│   ├── config/
│   │   ├── CloudinaryConfig.java    # Cloudinary bean config
│   │   ├── DataInitializer.java     # Seed default categories
│   │   ├── OpenApiConfig.java       # OpenAPI/Swagger config
│   │   └── SecurityConfig.java      # Spring Security rules
│   │
│   ├── controller/
│   │   ├── HomeController.java      # GET / → redirect to /products
│   │   ├── LoginController.java     # Login page
│   │   ├── ProductsController.java  # MVC CRUD for products
│   │   ├── CategoryController.java  # MVC CRUD for categories
│   │   ├── ProductRestController.java   # REST API for products
│   │   └── CategoryRestController.java  # REST API for categories
│   │
│   ├── dto/
│   │   ├── ApiResponse.java         # Generic response wrapper
│   │   ├── ProductRequest.java      # Product request body
│   │   └── CategoryRequest.java     # Category request body
│   │
│   ├── models/
│   │   ├── Product.java             # JPA entity
│   │   ├── Category.java            # JPA entity
│   │   └── ProductDTO.java          # Thymeleaf form DTO
│   │
│   └── services/
│       ├── ProductsRepository.java  # Spring Data JPA
│       ├── CategoryRepository.java  # Spring Data JPA
│       └── CloudinaryService.java   # Image upload service
│
├── src/main/resources/
│   ├── templates/                   # Thymeleaf HTML templates
│   ├── static/                      # Static assets
│   ├── application.properties       # Default config
│   ├── application-dev.properties   # Dev profile
│   └── application-local.properties # Local H2 profile
│
└── src/test/java/com/hend/store/
    ├── StoreApplicationTests.java
    └── services/ProductsRepositoryTest.java
```

---

## Deployment

### Docker Compose (Recommended for Local)

```bash
docker compose up -d
```

| Service | Port | Description |
|---------|------|-------------|
| `app` | `8080` | Spring Boot application |
| `mysql` | `3306` | MySQL 8.0 database |

### Heroku / Railway

```bash
git push heroku main
```

Pastikan env vars sudah di-set di dashboard platform.

### Docker Hub (via GitHub Actions)

Push ke branch `main` akan trigger CI/CD pipeline:

1. **Build & Test** — `mvn verify`
2. **Docker Build** — Multi-stage build
3. **Push** — Image ke Docker Hub

**Required GitHub Secrets:**

| Secret | Description |
|--------|-------------|
| `DOCKER_USERNAME` | Docker Hub username |
| `DOCKER_PASSWORD` | Docker Hub access token |

---

## Testing

```bash
# Jalankan semua test
./mvnw verify

# Jalankan test tertentu
./mvnw test -Dtest=ProductsRepositoryTest
```

**Test Coverage:**
- `StoreApplicationTests` — Context load test
- `ProductsRepositoryTest` — CRUD operations & search queries

> **Note:** Spring Boot 4.x menghapus `@DataJpaTest`, `@WebMvcTest`, dan `@AutoConfigureMockMvc`. Semua test menggunakan `@SpringBootTest`.

---

## Contributing

1. Fork repository ini
2. Buat branch baru: `git checkout -b feature/your-feature`
3. Commit perubahan: `git commit -m 'Add your feature'`
4. Push ke branch: `git push origin feature/your-feature`
5. Buka Pull Request

---

## License

Distributed under the MIT License. See `LICENSE` untuk informasi lebih lanjut.

---

<p align="center">
  Made with Spring Boot 4 + Thymeleaf + REST API
</p>
