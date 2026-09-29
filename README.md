# 🚗 Vehicle Service and Maintenance Tracking System (VSMTS)

A full-stack web application built with **Spring Boot** that enables vehicle owners to manage their vehicles, track service history, and connect with authorized service centers — all through a secure, role-based platform.

> **Team 14** | SEAP Project — Sprint 1

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Team Members](#team-members)
- [User Roles](#user-roles)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Sprint 1 Scope](#sprint-1-scope)
- [Testing](#testing)
- [Screenshots](#screenshots)

---

## Overview

The **Vehicle Service and Maintenance Tracking System (VSMTS)** addresses the common problem of vehicle owners losing track of their service records, upcoming maintenance schedules, and trusted service providers. This platform digitizes the entire vehicle maintenance lifecycle, providing a centralized hub for owners, service centers, and administrators.

### Problem Statement
Vehicle owners often rely on paper receipts and memory to track maintenance. This leads to missed services, warranty issues, and difficulty finding reliable service centers. VSMTS solves this by providing a digital platform where all stakeholders can collaborate efficiently.

---

## Features

### 🔐 Authentication & Security
- Secure user registration with email validation
- Password enforcement (minimum 8 characters, uppercase, lowercase, number, special character)
- BCrypt password encryption
- Spring Security-based login/logout
- Role-based access control (RBAC)

### 📊 Role-Based Dashboards
- **Vehicle Owner Dashboard** — View vehicles, service history, and manage profiles
- **Service Center Dashboard** — Manage service records and view assigned vehicles
- **Admin Dashboard** — System-wide overview with user and role statistics

### 🚙 Vehicle Management
- Add and manage vehicle profiles (make, model, year, registration number)
- View all registered vehicles under an owner's account

### 🔧 Service Records
- Log detailed service records (service type, date, cost, description)
- View complete service history for each vehicle
- Service centers can update and manage records

---

## Tech Stack

| Layer          | Technology                          |
|----------------|-------------------------------------|
| **Backend**    | Java 25, Spring Boot 3.1.5          |
| **Security**   | Spring Security, BCrypt             |
| **Frontend**   | Thymeleaf, HTML5, CSS3, Bootstrap   |
| **Database**   | MySQL 8.0                           |
| **ORM**        | Spring Data JPA / Hibernate         |
| **Build Tool** | Apache Maven                        |
| **Testing**    | JUnit 5, Selenium WebDriver         |
| **Coverage**   | JaCoCo                              |
| **VCS**        | Git & GitHub                        |
| **Agile Tool** | Jira (Scrum Board)                  |

---

## Team Members

| Name                | Role                    | Key Responsibilities                              |
|---------------------|-------------------------|----------------------------------------------------|
| **Meston Jose S**   | Scrum Master / Developer | User Registration, Login/Logout, Security Config   |
| **Mohamed Apsal M** | Developer               | Role-Based Dashboard, Add Vehicle Profile          |
| **Kevin Jeniston S**| Developer / QA          | Log Service Record, View Service History           |

---

## User Roles

| Role              | Access Level                                              |
|-------------------|-----------------------------------------------------------|
| **Vehicle Owner** | Register vehicles, view service history, manage profile   |
| **Service Center**| Log service records, view assigned vehicles               |
| **Administrator** | Full system access, user management, dashboard analytics  |

---

## Project Structure

```
VSMTS/
├── src/
│   ├── main/
│   │   ├── java/com/team14/vsmts/
│   │   │   ├── config/            # Security configuration
│   │   │   ├── controller/        # MVC controllers
│   │   │   │   ├── RegistrationController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── OwnerController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── ServiceCenterController.java
│   │   │   │   └── ProfileController.java
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── model/             # JPA Entities (User, Vehicle, ServiceRecord)
│   │   │   ├── repository/        # Spring Data JPA Repositories
│   │   │   ├── service/           # Business logic layer
│   │   │   └── VsmtsApplication.java
│   │   └── resources/
│   │       ├── static/            # CSS, JS, Images
│   │       └── templates/         # Thymeleaf HTML templates
│   └── test/                      # JUnit 5 test classes
├── pom.xml                        # Maven dependencies
└── README.md
```

---

## Getting Started

### Prerequisites
- **Java 17+** installed
- **MySQL 8.0** running on port `3307`
- **Apache Maven** installed
- **Git** installed

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/mestonjose/VSMTS.git
   cd VSMTS
   ```

2. **Set up the database environment variable:**
   ```bash
   # Windows PowerShell
   $env:DB_PASSWORD="your_mysql_password"

   # Linux / Mac
   export DB_PASSWORD=your_mysql_password
   ```

3. **Build and run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Open your browser and navigate to:**
   ```
   http://localhost:8080
   ```

5. **Register a new account** and start using the system!

---

## Sprint 1 Scope

Sprint 1 focused on building the core authentication and vehicle management foundation:

| Task ID  | User Story                    | Assignee         | Status |
|----------|-------------------------------|------------------|--------|
| VSMTS-1  | User Registration             | Meston Jose S    | ✅ Done |
| VSMTS-2  | User Login & Logout           | Meston Jose S    | ✅ Done |
| VSMTS-4  | Role-Based Dashboard Redirect | Mohamed Apsal M  | ✅ Done |
| VSMTS-5  | Add Vehicle Profile           | Mohamed Apsal M  | ✅ Done |
| VSMTS-8  | Log Service Record            | Kevin Jeniston S | ✅ Done |
| VSMTS-9  | View Service History          | Kevin Jeniston S | ✅ Done |

---

## Testing

### Unit Testing (JUnit 5)
- **Service Layer Tests** — Validates business logic for registration, login, and data operations
- **Controller Tests** — Validates REST endpoints using MockMvc
- **Test Scenarios:**
  - ✅ Positive: Successful login with correct credentials
  - ❌ Negative: Login fails with wrong password
  - 🔲 Boundary: Password must be at least 8 characters
  - ❌ Negative: Registration fails with duplicate email

### UI Automation (Selenium)
- Automated browser tests for critical user workflows
- End-to-end testing of registration and login flows

### Code Coverage (JaCoCo)
- Integrated JaCoCo plugin for code coverage analysis
- Coverage reports generated during Maven build

### Running Tests
```bash
mvn test
```

---

## Screenshots

> Screenshots of the working application will be added after each sprint review.

---

## License

This project is developed as part of the **Software Engineering and Agile Practices (SEAP)** course.

---

*Built with ❤️ by Team 14*
