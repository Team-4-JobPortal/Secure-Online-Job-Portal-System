Secure Online Job Portal
System


# Job Portal Spring MVC REST API - Complete Guide

## Project Overview

**Architecture**: Spring MVC + Hibernate + JWT Authentication  
**Purpose**: Job Portal application with role-based access (Candidates & Employers)  
**Database**: Relational database with Hibernate ORM  
**Security**: JWT token-based authentication with role-based authorization  

## Entity Relationships

```
User (1) ←→ (0..1) CandidateProfile
User (1) ←→ (0..1) EmployerProfile  
User (1) → (*) Job (as employer)
User (1) → (*) Application (as candidate)
Job (1) → (*) Application
```

## Complete API Endpoints Summary

### Authentication Endpoints (`/auth`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/register` | User registration | No |
| POST | `/auth/login` | User login with role validation | No |
| POST | `/auth/change-password` | Change password | No (validates old password) |
| GET | `/auth/user/me` | Get current user details | Yes |

### Job Endpoints (`/jobs`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/jobs/postJob` | Create job (Employer only) | Yes (Employer) |
| GET | `/jobs/list` | Get all jobs | No |
| GET | `/jobs/search` | Search jobs with filters | No |
| GET | `/jobs/{id}` | Get job by ID | No |
| PUT | `/jobs/{id}` | Update job | Yes |
| DELETE | `/jobs/{id}` | Delete job | Yes |
| GET | `/jobs/currentLoginEmployerJobPosted/list` | Get employer's jobs | Yes (Employer) |
| POST | `/jobs/{jobId}/apply` | Apply for job (Candidate only) | Yes (Candidate) |

### Application Endpoints (`/applications`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/applications/list` | Get all applications | Yes |
| GET | `/applications/{id}` | Get application by ID | Yes |
| POST | `/applications/apply` | Create application | Yes |
| PUT | `/applications/{id}` | Update application | Yes |
| DELETE | `/applications/{id}` | Delete application | Yes |
| GET | `/applications/my-applications` | Get candidate's applications | Yes (Candidate) |
| GET | `/applications/my-job-applications` | Get applications for employer's jobs | Yes (Employer) |
| GET | `/applications/candidate/stats` | Get candidate statistics | Yes (Candidate) |
| GET | `/applications/employer/stats` | Get employer statistics | Yes (Employer) |
| GET | `/applications/check-applied/{jobId}` | Check if already applied | Yes |
| PUT | `/applications/{id}/status` | Update application status | Yes (Employer) |
| DELETE | `/applications/{id}/withdraw` | Withdraw application | Yes (Candidate) |

### User & Profile Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/users` | Get all users | Yes |
| GET | `/users/role/{role}` | Get users by role | Yes |
| GET | `/users/{id}` | Get user by ID | Yes |
| PUT | `/users/{id}` | Update user | Yes |
| DELETE | `/users/{id}` | Delete user | Yes |
| PUT | `/users/update` | Update profile | Yes |
| GET | `/profile` | Get current user profile | Yes |

---

## Postman Testing JSON Examples

### 1. Register Candidate

```json
POST http://localhost:8080/auth/register
Content-Type: application/json

{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "9876543210",
    "password": "Password@123",
    "role": "candidate"
}
```

### 2. Register Employer

```json
POST http://localhost:8080/auth/register
Content-Type: application/json

{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@company.com",
    "phoneNumber": "9876543211",
    "password": "Password@456",
    "role": "employer"
}
```

### 3. Login Candidate

```json
POST http://localhost:8080/auth/login
Content-Type: application/json

{
    "email": "john.doe@example.com",
    "password": "Password@123",
    "role": "candidate"
}
```

**Expected Response:**
```json
{
    "status": "success",
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "role": "candidate"
}
```

### 4. Login Employer

```json
POST http://localhost:8080/auth/login
Content-Type: application/json

{
    "email": "jane.smith@company.com",
    "password": "Password@456",
    "role": "employer"
}
```

### 5. Post Job (Employer)

```json
POST http://localhost:8080/jobs/postJob
Content-Type: application/json
Authorization: Bearer {EMPLOYER_JWT_TOKEN}

{
    "title": "Senior Java Developer",
    "description": "We are looking for an experienced Java developer with Spring Boot expertise. The candidate should have strong problem-solving skills and experience with microservices architecture.",
    "location": "Bangalore",
    "min_salary": 80000,
    "max_salary": 120000,
    "deadline": "2025-10-15"
}
```

### 6. Get All Jobs

```json
GET http://localhost:8080/jobs/list
Content-Type: application/json
```

### 7. Search Jobs

```json
GET http://localhost:8080/jobs/search?keyword=java&location=bangalore&minSalary=70000&maxSalary=150000
Content-Type: application/json
```

### 8. Apply for Job (Candidate)

```json
POST http://localhost:8080/jobs/1/apply
Content-Type: application/json
Authorization: Bearer {CANDIDATE_JWT_TOKEN}

{
    "coverLetter": "I am very interested in this position as it aligns perfectly with my 5 years of Java development experience. I have worked extensively with Spring Boot, microservices, and have a proven track record of delivering high-quality software solutions."
}
```

### 9. Get Candidate Applications

```json
GET http://localhost:8080/applications/my-applications
Content-Type: application/json
Authorization: Bearer {CANDIDATE_JWT_TOKEN}
```

### 10. Get Candidate Stats

```json
GET http://localhost:8080/applications/candidate/stats
Content-Type: application/json
Authorization: Bearer {CANDIDATE_JWT_TOKEN}
```

**Expected Response:**
```json
{
    "totalApplications": 5,
    "pendingApplications": 2,
    "shortlistedApplications": 1,
    "rejectedApplications": 2
}
```

### 11. Update Application Status (Employer)

```json
PUT http://localhost:8080/applications/1/status?status=accepted
Content-Type: application/json
Authorization: Bearer {EMPLOYER_JWT_TOKEN}
```

### 12. Change Password

```json
POST http://localhost:8080/auth/change-password
Content-Type: application/json

{
    "email": "john.doe@example.com",
    "role": "candidate",
    "oldPassword": "Password@123",
    "newPassword": "NewPassword@789"
}
```

### 13. Update Profile

```json
PUT http://localhost:8080/users/update
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
    "firstName": "John",
    "lastName": "Doe",
    "phone": "9876543210",
    "location": "Mumbai",
    "skills": "Java, Spring Boot, Microservices, Docker, Kubernetes",
    "experience": "5-10"
}
```

### 14.Withdraw Application
```json
DELETE http://localhost:8080/applications/52/withdraw
Content-Type: application/json
Authorization: Bearer {CANDIDATE_JWT_TOKEN}
```

---

## Validation & Exception Handling (For Viva)

### Bean Validation Annotations

#### User Entity Validations
- `@NotBlank(message="First name is required")` - firstName
- `@NotBlank(message="Email is required")` + `@Email` - email  
- `@Pattern` for password strength (min 8 chars, uppercase, lowercase, number, special char)

#### Job Entity Validations
- `@NotBlank` + `@Size(min=3, max=150)` - title
- `@NotBlank` + `@Size(max=1000)` - description
- `@NotNull` - deadline

#### LoginRequest Validations
- `@NotBlank` + `@Email` - email
- `@NotBlank` - password, role

### Custom Business Validations
1. **Role-based access control** - Controllers validate user roles
2. **JWT token validation** - JwtAuthenticationFilter
3. **Duplicate application check** - `hasUserAppliedToJob()`
4. **Application status validation** - Only pending applications can be updated
5. **User ownership validation** - Users can only modify their own data
6. **Cover letter required** - For job applications

### Exception Handling

#### Custom Exceptions
- **JobNotFoundException** - When job ID not found
- **UserEmailNotFoundException** - When email not found in database

#### Global Exception Handler (`@ControllerAdvice`)
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleJobNotFound(JobNotFoundException ex) {
        // Returns HTTP 404 with custom error message
    }
    
    @ExceptionHandler(UserEmailNotFoundException.class)  
    public ResponseEntity<Map<String, String>> EmailNotFound(UserEmailNotFoundException ex) {
        // Returns HTTP 404 with custom error message
    }
}
```

#### Controller-Level Exception Handling
- Try-catch blocks in authentication methods
- `BadCredentialsException` handling in login
- HTTP status codes: 401 (Unauthorized), 403 (Forbidden), 404 (Not Found), 400 (Bad Request), 409 (Conflict), 500 (Internal Server Error)

### Security Implementation

#### JWT Authentication Flow
1. User login with credentials
2. `AuthenticationManager` validates credentials
3. JWT token generated and returned
4. Subsequent requests include Bearer token in Authorization header
5. `JwtAuthenticationFilter` validates token
6. `SecurityContext` set with authenticated user

#### Role-Based Access Control
- **Candidate Role**: Apply for jobs, view own applications, update profile
- **Employer Role**: Post jobs, manage applications for their jobs, view employer statistics
- Role validation in controller methods using `Authentication.getName()`

### Database Design & Hibernate Features

#### Key Hibernate Features Used
- **SessionFactory & Session Management** - Database connection handling
- **Criteria API** - Dynamic query building for job search
- **HQL (Hibernate Query Language)** - Complex queries with joins
- **Cascade Operations** - `CascadeType.ALL` for profile relationships
- **Lazy/Eager Loading** - `FetchType.LAZY` and `FetchType.EAGER`
- **Transaction Management** - `@Transactional` annotation

#### Important Mappings
- `@OneToOne` with `@MapsId` for User-CandidateProfile relationship
- `@ManyToOne` with `@JoinColumn` for Application-User and Application-Job
- `@JsonIgnore` and `@JsonBackReference` to prevent infinite recursion

---

## Testing Workflow in Postman

1. **Register users** (both candidate and employer)
2. **Login to get JWT tokens** (save tokens for subsequent requests)
3. **Post jobs** as employer
4. **Search and view jobs** as candidate
5. **Apply for jobs** as candidate
6. **View applications** from both perspectives
7. **Update application status** as employer
8. **Test validation errors** with invalid data
9. **Test authorization** by accessing endpoints with wrong roles
10. **Test exception handling** with invalid IDs or data

Remember to replace `{JWT_TOKEN}`, `{CANDIDATE_JWT_TOKEN}`, and `{EMPLOYER_JWT_TOKEN}` with actual tokens received from login responses.
