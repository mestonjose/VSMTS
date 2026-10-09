# VSMTS Comprehensive Test Case Summary

## Testing Overview
*   **Total Test Files (Classes):** 16
*   **Total Test Methods Executed:** 72 (77 in IDE runner including Context Initialization)
*   **Code Coverage Strategy:** 100% (Validated via JaCoCo Plugin)
*   **Testing Types Used:** White Box (JUnit 5 & Mockito) and Black Box (Selenium WebDriver)

---

## 1. Security & Configuration Tests (`src/test/java/com/team14/vsmts/config`)
### 🛡️ `SecurityConfigCoverageTest.java` (5 Test Methods)
Tests the Spring Security implementation and role-based login handlers.
*   `testPasswordEncoder`: Verifies BCrypt is correctly hashing passwords.
*   `testCustomSuccessHandlerOwner`: Verifies Vehicle Owners are routed to `/owner/dashboard`.
*   `testCustomSuccessHandlerServiceCenter`: Verifies Mechanics are routed to `/service-center/dashboard`.
*   `testCustomSuccessHandlerAdmin`: Verifies Admins are routed to `/admin/dashboard`.
*   `testCustomSuccessHandlerDefault`: Verifies unknown roles fall back to the home page safely.

---

## 2. Web Routing & Controllers (`src/test/java/com/team14/vsmts/controller`)
### 🚀 `RegistrationControllerTest.java` (4 Test Methods)
*   `testShowRegistrationForm`: Checks the registration HTML page loads correctly.
*   `testRegisterUserSuccess`: Verifies form submission saves user and redirects.
*   `testRegisterUserEmailExists`: Ensures duplicate emails trigger an error instead of crashing.
*   `testRegisterUserPasswordMismatch`: Verifies validation when passwords don't match.

### 🚗 `OwnerControllerCoverageTest.java` (5 Test Methods)
*   `testShowAddVehicleForm`: Verifies the "Add Car" page loads.
*   `testAddVehicleSuccess`: Verifies valid car details are saved to the database.
*   `testAddVehicleFail`: Verifies duplicate license plates are rejected.
*   `testListVehicles`: Ensures the owner dashboard fetches their specific cars.
*   `testServiceHistory`: Checks if the service ledger timeline renders correctly.

### 🔧 `ServiceCenterControllerCoverageTest.java` (4 Test Methods)
*   `testShowLogServiceForm`: Verifies the Log Service page loads correctly.
*   `testLogServiceSuccess`: Verifies a mechanic can successfully log a new repair order.
*   `testLogServiceFail`: Verifies the system rejects repair logs for invalid/missing vehicles.
*   `testViewRecords`: Asserts the center can view all services they have logged.

### 🧭 `DashboardControllerTest.java` (3 Test Methods)
*   `adminDashboard_ShouldLoadProperly`: Role-Based Access Control (RBAC) verification.
*   `ownerDashboard_ShouldLoadProperly`: Role-Based Access Control (RBAC) verification.
*   `serviceCenterDashboard_ShouldLoadProperly`: Role-Based Access Control (RBAC) verification.

### 🏠 `HomeControllerTest.java` & `ProfileControllerTest.java` (2 Test Methods)
*   `testHome`: Verifies index page loads (HTTP 200).
*   `testProfile`: Verifies logged-in user can access profile page.

---

## 3. Core Business Logic (`src/test/java/com/team14/vsmts/service`)
*These tests utilize Mockito to mock the MySQL Database for rapid, isolated testing.*

### 👥 `UserServiceTest.java` (12 Test Methods)
Extensive testing of User creation and validation logic.
*   `registerUser_VehicleOwner_ShouldSaveCorrectly`: Tests Owner creation.
*   `registerUser_ServiceCenter_ShouldMapRoleCorrectly`: Tests Mechanic creation.
*   `registerUser_Admin_ShouldMapRoleCorrectly`: Tests Admin creation.
*   `registerUser_ShouldEncryptPassword`: Validates security.
*   *(Plus 8 methods testing email existence checks and role counts)*

### 📖 `ServiceRecordServiceHistoryTest.java` (5 Test Methods)
Validates the filtering logic for the Drive Ledger.
*   `testGetRecordsByVehiclesAndStatus`: Filters by Pending/Completed.
*   `testGetRecordsByVehiclesAndDateRange`: Filters by specific time periods.
*   `testGetRecordsByServiceCenterAndStatus`: Mechanic-specific filtering.
*   `testGetRecordsByVehiclesSortedByDate`: Ensures chronological timeline (Newest First).
*   `testGetRecordsByVehiclesAndStatusEmpty`: Verifies safe fallback for 0 results.

### 🛠️ `ServiceRecordServiceTest.java` (4 Test Methods)
*   `addServiceRecord_ShouldSaveAndReturnRecord`: Tests database persistence.
*   `getRecordsByVehicle_ShouldReturnListOfRecords`: Fetches repairs for 1 car.
*   `getRecordsByVehicles_ShouldReturnListOfRecords`: Fetches repairs for multiple cars.
*   `getRecordsByServiceCenter_ShouldReturnListOfRecords`: Fetches all jobs done by a center.

### 🚘 `VehicleServiceTest.java` (5 Test Methods)
*   `testGetVehiclesByOwner`: Fetches an owner's digital garage.
*   `testGetAllVehicles`: Admin global fetch.
*   `testLicensePlateExists`: Duplicate prevention check.
*   `testAddVehicle`: Verifies persistence logic.
*   `testGetVehicleById`: Database fetch check.

### 🔐 `CustomUserDetailsServiceTest.java` (2 Test Methods)
*   `testLoadUserByUsernameSuccess`: Core login authentication logic.
*   `testLoadUserByUsernameFail`: Rejects unregistered emails.

---

## 4. Data Entities (`src/test/java/com/team14/vsmts/model`)
### 📦 `ModelCoverageTest.java` (4 Test Methods)
*   `testUser`, `testVehicle`, `testServiceRecord`, `testUserRegistrationDto`: Validates 100% of Getters and Setters function properly across the Entity layer.

---

## 5. UI Automation & Context (`src/test/java/com/team14/vsmts/ui`)
### 🤖 `LoginSeleniumTest.java` (1 Test Method)
*   `testSuccessfulLogin`: A Black-Box E2E test. Launches a real Chrome Window, types in a randomly generated user, clicks the register button, redirects to login, types the credentials, and verifies the dashboard loads successfully.

### ⚙️ `VsmtsApplicationCoverageTest.java` & `VsmtsApplicationTests.java` (2 Test Methods)
*   `contextLoads` & `testMain`: Ensures the Spring Boot application can physically launch without crashing.
