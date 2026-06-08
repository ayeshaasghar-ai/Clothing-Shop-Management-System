# ClothWare ERP — Full Stack (Java 25 Backend + HTML Frontend)

## Project Structure

```
clothware-backend/          ← Java 25 + Spring Boot 3.3 Backend
clothware-frontend-connected/ ← Updated HTML Frontend (API-connected)
```

---

## ✅ Prerequisites

| Tool | Version | Download |
|------|---------|----------|
| **JDK** | **25** | https://jdk.java.net/25/ |
| **Maven** | 3.9+ | https://maven.apache.org/ |
| **Browser** | Any modern | — |

---

## 🚀 Step 1 — Start the Java Backend

```bash
cd clothware-backend
mvn spring-boot:run
```

**First run** downloads dependencies (~2 min). After that, you'll see:
```
✅ ClothWare backend started. Demo data seeded.
📧 Login: admin@clothware.com | Password: admin123
🌐 API: http://localhost:8080/api
🗄️  H2 Console: http://localhost:8080/h2-console
```

---

## 🌐 Step 2 — Open the Frontend

Serve the `clothware-frontend-connected/` folder with any static server:

**Option A — VS Code Live Server (recommended)**
1. Install "Live Server" extension in VS Code
2. Right-click `index.html` → "Open with Live Server"
3. Open: http://127.0.0.1:5500

**Option B — Python**
```bash
cd clothware-frontend-connected
python -m http.server 5500
# Open: http://localhost:5500
```

**Option C — Node.js**
```bash
npx serve clothware-frontend-connected -p 5500
```

---

## 🔑 Login

| Field | Value |
|-------|-------|
| Email | `admin@clothware.com` |
| Password | `admin123` |

---

## 📡 REST API Endpoints

All protected endpoints require `Authorization: Bearer <JWT>` header.

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login, get JWT token |

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get by ID |
| GET | `/api/products/low-stock` | Items with stock < 10 |
| POST | `/api/products` | Create product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Customers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customers` | List all |
| POST | `/api/customers` | Create |
| PUT | `/api/customers/{id}` | Update |
| DELETE | `/api/customers/{id}` | Delete |

### Employees
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | List all |
| POST | `/api/employees` | Create |
| PUT | `/api/employees/{id}` | Update |
| DELETE | `/api/employees/{id}` | Delete |

### Orders & Billing
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | List all orders |
| GET | `/api/orders/recent` | Last 5 orders |
| POST | `/api/orders/generate-bill` | Create order + deduct stock |
| DELETE | `/api/orders/{id}` | Delete order |

### Dashboard & Reports
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dashboard/stats` | Summary stats |
| GET | `/api/dashboard/report` | Full report data |

### Settings
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/settings` | Get shop settings |
| PUT | `/api/settings` | Update settings |

---

## 🗄️ Database

**Default: H2 in-memory** (data resets on restart — great for dev)
- Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:clothwaredb`

**Switch to MySQL** — edit `application.properties`:
```properties
# Comment out H2 lines, uncomment MySQL lines, and set your password
spring.datasource.url=jdbc:mysql://localhost:3306/clothwaredb
spring.datasource.username=root
spring.datasource.password=yourpassword
```
Add MySQL dependency to `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 🏗️ Architecture

```
Frontend (HTML/JS)
    │  fetch() with JWT
    ▼
Spring Boot (port 8080)
    ├── SecurityConfig (JWT filter, CORS)
    ├── AuthController → /api/auth/*
    ├── ProductController → /api/products/*
    ├── CustomerController → /api/customers/*
    ├── EmployeeController → /api/employees/*
    ├── OrderController → /api/orders/*
    ├── DashboardController → /api/dashboard/*
    └── SettingsController → /api/settings/*
         │
         ▼
    JPA Repositories
         │
         ▼
    H2 / MySQL Database
```

---

## ⚙️ Build for Production

```bash
cd clothware-backend
mvn clean package -DskipTests
java -jar target/clothware-backend-1.0.0.jar
```
