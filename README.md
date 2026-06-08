# 👗 Clothing Shop Management System

A full-stack ERP system built for clothing stores to manage products, customers, employees, orders, billing, and reports — all in one place.

---

## 🚀 Features

- 🔐 **Secure Login** — JWT-based authentication
- 📦 **Product & Inventory Management** — Add, update, delete products with stock tracking
- 👥 **Customer Management** — Keep full records of customers
- 👨‍💼 **Employee Management** — Manage your store staff
- 🧾 **Orders & Billing** — Generate bills and auto-deduct stock
- 📊 **Dashboard & Reports** — Live stats and business summary
- ⚙️ **Shop Settings** — Customize your store information

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | HTML, CSS, JavaScript |
| Backend | Java 25, Spring Boot 3.3 |
| Security | JWT (JSON Web Tokens) |
| Database | H2 (in-memory) / MySQL |
| Build Tool | Maven |

---

## 📁 Project Structure
```
Clothing-Shop-Management-System/
├── backend/
│   ├── src/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── pom.xml
└── frontend/
├── index.html
├── login.html
├── dashboard.html
├── api.js
└── pages/
├── products.html
├── customers.html
├── employees.html
├── orders.html
├── billing.html
├── inventory.html
├── reports.html
└── settings.html
```
---

## ⚙️ How to Run

### 1️⃣ Start the Backend
cd backend
mvn spring-boot:run

Backend will start at: http://localhost:8080

### 2️⃣ Open the Frontend

Open `frontend/index.html` using VS Code Live Server or any browser.

---

## 🔑 Default Login

| Field | Value |
|-------|-------|
| Email | admin@clothware.com |
| Password | admin1234 |

---

## 🗄️ Database

By default uses H2 in-memory database (no setup needed).

To switch to MySQL, update `application.properties`:
spring.datasource.url=jdbc:mysql://localhost:3306/clothwaredb
spring.datasource.username=root
spring.datasource.password=your_password

---

## 👩‍💻 Developed By

Made with ❤️ as a university project.
