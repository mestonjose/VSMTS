# VSMTS Defect Log

| Defect ID | Date Found | Component | Description | Severity | Status | Fixed By | Fix Description |
|-----------|------------|-----------|-------------|----------|--------|----------|-----------------|
| DEF-001 | 2026-09-25 | User Registration | Email uniqueness not enforced during registration. | High | **CLOSED** | Meston Jose S | Added `existsByEmail` check in `UserService` and validated in controller. |
| DEF-002 | 2026-10-02 | Login Module | User details failing to load due to incorrect Spring Security `username` parameter mapping. | Critical | **CLOSED** | Meston Jose S | Updated `SecurityConfig` to explicitly map `.usernameParameter("email")`. |
| DEF-003 | 2026-10-03 | UI/UX | Horizontal scroll clipping on the service record table causing overflow. | Medium | **CLOSED** | Meston Jose S | Applied `overflow-x-auto` to the parent container in Tailwind CSS. |
| DEF-004 | 2026-10-03 | Dashboard | Role-based redirection sending Service Centers to Vehicle Owner dashboard. | High | **CLOSED** | Mohamed Apsal M | Implemented custom `AuthenticationSuccessHandler` to route by role. |
| DEF-005 | 2026-10-04 | Database | Hardcoded database password exposed in application configuration. | High | **MITIGATED**| Meston Jose S | Used `git update-index --assume-unchanged` to prevent tracking `application.properties`. |
