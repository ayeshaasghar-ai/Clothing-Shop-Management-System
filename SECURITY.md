# 🔒 Security Policy

## 📌 Supported Versions

The following versions of Clothing Shop Management System are currently supported with security updates:

| Version | Supported |
|---------|-----------|
| 1.0.x   | ✅ Yes    |

---

## 🐛 Reporting a Vulnerability

If you discover a security vulnerability in this project, please do NOT open a public GitHub Issue. Instead, follow the steps below:

### 📧 How to Report

1. Send an email with the subject line: **[SECURITY] Vulnerability Report — Clothing Shop Management System**
2. Include the following details in your report:
   - Description of the vulnerability
   - Steps to reproduce the issue
   - What data or functionality is affected
   - Possible impact of the vulnerability
   - Your suggested fix (if any)

### ⏱️ What to Expect

- You will receive an acknowledgement within **48 hours**
- We will investigate and keep you updated on the progress
- Once fixed, we will notify you and credit you (if you wish) in the release notes

---

## 🔐 Security Best Practices for This Project

If you are running this project locally or deploying it, please follow these guidelines:

- **Change default credentials** — Do not use `admin@clothware.com / admin123` in production
- **Use HTTPS** — Always run the backend behind HTTPS in production
- **Keep dependencies updated** — Regularly update Maven dependencies
- **Secure your database** — Use a strong MySQL password in production
- **Hide sensitive files** — Never upload `application.properties` with real credentials to GitHub
- **JWT Secret** — Change the default JWT secret key before deploying

---

## ⚠️ Known Security Considerations

- This project uses **H2 in-memory database** by default — not recommended for production
- Default login credentials are hardcoded for demo purposes only — must be changed before production use
- The H2 console (`/h2-console`) should be disabled in production

---

## 🛡️ Dependencies & Vulnerabilities

We use the following security-related dependencies:

| Dependency | Purpose |
|------------|---------|
| Spring Security | Authentication & Authorization |
| JWT (JSON Web Token) | Secure API access |
| BCrypt | Password hashing |

To check for known vulnerabilities in dependencies, run:

    mvn dependency-check:check

---

## 📬 Contact

For any security-related concerns, please reach out via GitHub Issues (for non-sensitive matters) or privately for sensitive vulnerabilities.

---

Thank you for helping keep this project secure! 🙏
