# Store — Spring Boot 4 + Thymeleaf

Aplikasi web manajemen produk dan kategori dengan upload gambar ke Cloudinary, autentikasi Spring Security, dan database MySQL/H2.

## Stack

| Teknologi | Versi |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.1 |
| Spring Security | 7.0.2 |
| Thymeleaf | 3.1.3 |
| Hibernate ORM | 7.2.0 |
| MySQL | 8.0+ (prod), H2 (dev/test) |
| Docker | multi-stage build |
| Cloudinary | 1.33.0 (opsional) |

## Struktur Proyek

```
src/main/java/com/hend/store/
  config/        CloudinaryConfig, DataInitializer, SecurityConfig
  controller/    CategoryController, HomeController, LoginController, ProductsController
  models/        Category, Product, ProductDTO
  services/      CategoryRepository, CloudinaryService, ProductsRepository
  StoreApplication.java

src/main/resources/
  templates/     login.html, products/, categories/, error/
  application.properties
  application-dev.properties
  application-local.properties

src/test/java/com/hend/store/
  StoreApplicationTests.java
  services/ProductsRepositoryTest.java
```

## Fitur

- **Manajemen Produk** — CRUD produk dengan nama, brand, harga, deskripsi, upload gambar
- **Manajemen Kategori** — CRUD kategori (hanya ADMIN)
- **Search** — cari produk by nama atau brand (case-insensitive)
- **Autentikasi** — login form, role ADMIN & USER
- **Error Pages** — halaman 403, 404, 500 kustom
- **Actuator** — health endpoint publik
- **Optional Cloudinary** — upload gambar berfungsi tanpa akun Cloudinary (endpoint produk tetap jalan)

## Cara Jalankan

### 1. Local (H2 — tanpa MySQL, tanpa Cloudinary)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Akses [http://localhost:8080](http://localhost:8080). Data disimpan di `./data/store.mv.db`.

### 2. Development (MySQL local)

Jalankan MySQL di `localhost:3306`, database `store`, lalu:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Konfigurasi user MySQL via env var `MYSQL_USER` (default `root`) dan `MYSQL_PASSWORD` (default kosong).

### 3. Production

Set environment variables:

```bash
MYSQL_URL=jdbc:mysql://host:port/db?...
MYSQL_USER=user
MYSQL_PASSWORD=pass
CLOUDINARY_CLOUD_NAME=xxx    # opsional
CLOUDINARY_API_KEY=xxx       # opsional
CLOUDINARY_API_SECRET=xxx    # opsional
```

Lalu:

```bash
mvn package -DskipTests
java -jar target/store-*.jar
```

### 4. Docker Compose (MySQL + App)

```bash
docker compose up -d
```

Aplikasi akan jalan di `localhost:8080`, MySQL di `localhost:3306`. Set Cloudinary env vars di `.env` jika perlu upload gambar.

## Akun Demo

| Username | Password | Role |
|---|---|---|
| admin | admin123 | ADMIN |
| user | user123 | USER |

- **ADMIN** bisa akses semua fitur, termasuk CRUD kategori
- **USER** hanya bisa lihat dan CRUD produk

## API Endpoint Publik

```
GET /actuator/health   → {"status":"UP"}
```

## Profile

| Profile | Database | Keterangan |
|---|---|---|
| `local` | H2 file-based | Tidak perlu MySQL, data persist |
| `dev` | MySQL localhost | Untuk development dengan MySQL |
| `test` | H2 in-memory | Otomatis dipakai saat `mvn test` |
| (default) | MySQL via env vars | Untuk production |

## Deploy

### Railway / Heroku

```bash
# Procfile dan system.properties sudah tersedia
git push heroku main
```

Set env vars `MYSQL_URL`, `MYSQL_USER`, `MYSQL_PASSWORD`, dan Cloudinary vars di dashboard platform.

### Docker Hub (via GitHub Actions)

Push ke branch `main` / `master` akan trigger CI yang menjalankan test, build Docker image, dan push ke DockerHub. Tambahkan secrets di repository:

- `DOCKER_USERNAME`
- `DOCKER_PASSWORD`

## Testing

```bash
mvn verify
```

Menjalankan 3 test class: context load, repository CRUD, dan search.

Catatan: Spring Boot 4.x menghapus `@DataJpaTest`, `@WebMvcTest`, dan `@AutoConfigureMockMvc`. Semua test menggunakan `@SpringBootTest`.

## Catatan

- Cloudinary bersifat opsional — jika env var `CLOUDINARY_CLOUD_NAME` tidak diset, upload gambar akan menghasilkan error, tetapi endpoint produk lain tetap berfungsi.
- CSRF aktif untuk semua endpoint kecuali `/h2-console/**`.
- Halaman `/categories` hanya bisa diakses oleh user dengan role ADMIN.
