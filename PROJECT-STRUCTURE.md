# Job Portal - Spring Boot Project Structure

## 📁 Project Architecture

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── JobPortalApplication.java     // Main Spring Boot application class
│   │           ├── controller/                   // REST API and Web controllers
│   │           │   ├── ApplicationController.java
│   │           │   ├── AuthController.java
│   │           │   ├── JobController.java
│   │           │   ├── ProfileController.java
│   │           │   ├── UserController.java
│   │           │   └── WebController.java        // HTML template routes
│   │           ├── service/                      // Business logic layer
│   │           │   ├── ApplicationService.java
│   │           │   ├── JobService.java
│   │           │   ├── UserService.java
│   │           │   └── impl/
│   │           │       ├── ApplicationServiceImpl.java
│   │           │       ├── JobServiceImpl.java
│   │           │       └── UserServiceImpl.java
│   │           ├── repository/                   // Data access layer (Spring Data JPA)
│   │           │   ├── ApplicationRepository.java
│   │           │   ├── CandidateProfileRepository.java
│   │           │   ├── EmployerProfileRepository.java
│   │           │   ├── JobRepository.java
│   │           │   └── UserRepository.java
│   │           ├── entity/                       // JPA entities
│   │           │   ├── Application.java
│   │           │   ├── CandidateProfile.java
│   │           │   ├── EmployerProfile.java
│   │           │   ├── Job.java
│   │           │   └── User.java
│   │           ├── dto/                          // Data Transfer Objects
│   │           │   ├── ApplicationDto.java
│   │           │   ├── CandidateDto.java
│   │           │   ├── EmployerDto.java
│   │           │   ├── JobDto.java
│   │           │   ├── LoginRequest.java
│   │           │   ├── UpdateCandidateDto.java
│   │           │   └── UserDto.java
│   │           ├── security/                     // Security configuration
│   │           │   ├── CustomUserDetailsService.java
│   │           │   ├── JwtAuthenticationFilter.java
│   │           │   ├── JwtAuthEntryPoint.java
│   │           │   ├── JwtUtil.java
│   │           │   └── SecurityConfig.java
│   │           ├── exception/                    // Custom exceptions
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   ├── JobNotFoundException.java
│   │           │   ├── NegativeSalaryException.java
│   │           │   ├── UserEmailExistException.java
│   │           │   └── UserEmailNotFoundException.java
│   │           └── enums/                        // Enumerations
│   │               └── ExperienceLevel.java
│   │
│   └── resources/
│       ├── static/                               // Static assets (CSS, JS, images)
│       │   ├── css/
│       │   └── js/
│       ├── templates/                            // HTML templates (Thymeleaf)
│       │   ├── index.html                        // Home page
│       │   ├── login.html                        // Login page
│       │   └── register.html                     // Registration page
│       └── application.properties                // Application configuration
│
└── test/                                         // Test classes (to be added)
```

## 🚀 Key Features

### ✅ **Completed Architecture**
- **Spring Boot 3.2.0** with Java 17
- **Spring Data JPA** repositories (replaced DAOs)
- **Spring Security** with JWT authentication
- **Thymeleaf** templating engine for HTML pages
- **PostgreSQL** database integration
- **REST API** endpoints for mobile/frontend integration
- **HTML templates** with embedded CSS and JavaScript

### 🎨 **Frontend Features**
- **Responsive design** with modern CSS
- **Embedded CSS and JavaScript** in HTML files (as requested)
- **Interactive forms** with client-side validation
- **JWT token management** in localStorage
- **Role-based navigation** (Candidate/Employer)

### 🔧 **Backend Features**
- **Repository pattern** with Spring Data JPA
- **Service layer** with business logic
- **JWT-based authentication**
- **Role-based authorization**
- **Exception handling** with custom exceptions
- **Database transactions** with @Transactional

## 📋 **API Endpoints**

### **Authentication**
- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `POST /auth/change-password` - Change password
- `GET /auth/user/me` - Get current user info

### **Jobs**
- `GET /jobs/list` - List all jobs
- `GET /jobs/search` - Search jobs
- `GET /jobs/{id}` - Get job details
- `POST /jobs/postJob` - Post new job (Employer only)
- `DELETE /jobs/{id}` - Delete job (Employer only)
- `POST /jobs/{jobId}/apply` - Apply for job

### **Applications**
- `GET /applications/my-applications` - Get my applications
- `GET /applications/my-job-applications` - Get applications for my jobs
- `PUT /applications/{id}/status` - Update application status
- `DELETE /applications/{id}/withdraw` - Withdraw application

### **Web Pages**
- `GET /` - Home page
- `GET /login` - Login page
- `GET /register` - Registration page
- `GET /candidate-dashboard` - Candidate dashboard
- `GET /employer-dashboard` - Employer dashboard

## 🗑️ **Removed Files**
- ❌ `src/main/webapp/` - Old JSP webapp directory
- ❌ `src/main/java/com/example/dao/` - Old DAO layer
- ❌ `src/main/java/com/example/helper/` - Old Hibernate utilities
- ❌ `src/main/java/com/example/controller/InitialController.java` - Redundant controller
- ❌ `target/` - Build artifacts
- ❌ `pom-spring-boot.xml` - Temporary file

## 🎯 **Next Steps**
1. **Add dashboard templates** for candidates and employers
2. **Implement job details page** with application functionality
3. **Add profile management** pages
4. **Create comprehensive test suite**
5. **Add API documentation** with Swagger/OpenAPI
6. **Implement file upload** for resumes
7. **Add email notifications** for applications

## 🚀 **How to Run**
```bash
# Build the project
mvn clean package

# Run the application
java -jar target/job-portal-0.0.1-SNAPSHOT.jar

# Or run with Maven
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`
