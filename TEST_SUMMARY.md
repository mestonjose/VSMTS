# VSMTS Test Case Summary

## Overview
- **Total Test Files:** 16
- **Total Test Methods:** 72 Automated Test Cases (Execution runner logs up to 77 including Spring Context loads)
- **Code Coverage Target:** 100% (Verified via JaCoCo)
- **Testing Methodologies:** White Box (Unit & Integration) and Black Box (End-to-End UI)
- **Frameworks Used:** JUnit 5, Mockito, Spring Boot Test, Selenium WebDriver

## Core Modules Tested

### 1. Security & Configuration (/config)
*   **SecurityConfigCoverageTest**: Verifies that unauthorized users are successfully blocked from accessing protected URLs (like /owner/ and /service-center/).

### 2. Web Routing & Controllers (/controller)
*   **DashboardControllerTest**: Validates Role-Based Access Control (RBAC) routing, ensuring Vehicle Owners and Service Centers are redirected to their respective dashboards.
*   **OwnerControllerCoverageTest**: Verifies the Owner Dashboard successfully loads the user's specific vehicles from the database.
*   **ServiceCenterControllerCoverageTest**: Asserts the Service Center dashboard loads correctly and handles HTTP POST requests for new service records.
*   **RegistrationControllerTest**: Asserts correct web routing upon successful registration.
*   **HomeControllerTest & ProfileControllerTest**: Validates landing page and profile page rendering.

### 3. Business Logic & Services (/service)
*   **CustomUserDetailsServiceTest**: Verifies login authentication logic and database retrieval by email.
*   **UserServiceTest**: Validates secure user registration, BCrypt password hashing, and duplicate email prevention.
*   **VehicleServiceTest**: Validates the business logic for adding a new vehicle (Make, Model, Year, VIN) and mapping it to the correct Vehicle Owner.
*   **ServiceRecordServiceTest**: Validates the Service Center's ability to successfully log new repair orders (Cost, Type, Status).
*   **ServiceRecordServiceHistoryTest**: Verifies the "Drive Ledger" history timeline can be accurately sorted by Date (descending) and filtered by Status (e.g., PENDING vs COMPLETED).

### 4. Data Models (/model)
*   **ModelCoverageTest**: Validates the integrity of all Java Data Entities (User, Vehicle, ServiceRecord, UserRegistrationDto) to guarantee 100% structural code coverage and verify getter/setter logic.

### 5. Application Context & UI Automation
*   **VsmtsApplicationTests & VsmtsApplicationCoverageTest**: Verifies the Spring Boot Application Context loads without crashing.
*   **LoginSeleniumTest** *(Black Box)*: Simulates a real Google Chrome browser interacting with the UI to complete an end-to-end registration and login flow.
