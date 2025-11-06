# BOMS - Business Operations Management System

## Overview
Complete implementation of BOMS with all requirements:
- Role-based access control (Admin, Sales Team, Executive Team)
- Contact inquiry management with email notifications
- Sales proposal workflow
- Executive approval process
- Admin user management

## Database Tables
- `boms_users` - User management
- `boms_contact_inquiries` - Customer inquiries
- `boms_project_proposals` - Project proposals

## Features Implemented

### ✅ Contact Module
- Public contact form (no login required)
- Email notification to neelureddy369@gmail.com
- Stores in boms_contact_inquiries table

### ✅ Sales Module
- View and manage contact inquiries
- Status management: Pending → In Discussion → Converted → Rejected
- Create proposals only for CONVERTED inquiries
- Assign inquiries to sales team members

### ✅ Executive Module
- Review proposals with PENDING status
- Approve/Reject functionality
- Email notification to client on approval

### ✅ Admin Module
- Full system access
- User management (create, enable/disable)
- View all inquiries and proposals
- System statistics dashboard

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Database Setup
1. Start MySQL server
2. Database `boms_db` will be created automatically
3. Tables will be created on first run

### Email Configuration
Update `src/main/resources/application.properties`:
```properties
spring.mail.username=neelureddy369@gmail.com
spring.mail.password=your-gmail-app-password
```

### Running the Application
```bash
# Compile
mvn clean compile

# Run
mvn spring-boot:run

# Or use the batch file
run.bat
```

### Access URLs
- Application: http://localhost:8081
- Contact Form: http://localhost:8081/contact
- Login: http://localhost:8081/login

### Default Users
- **Admin:** admin/admin123
- **Sales:** sales1/sales123
- **Executive:** executive1/exec123

## Workflow
1. **Guest** submits contact inquiry → Email sent to neelureddy369@gmail.com
2. **Sales** manages inquiries → Updates status to CONVERTED
3. **Sales** creates proposal for converted inquiries
4. **Executive** approves/rejects proposals → Email sent to client on approval
5. **Admin** manages users and views all data

## Technical Stack
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Bootstrap 5
- Maven

## Security
- Role-based access control
- Password encryption (BCrypt)
- CSRF protection disabled for API endpoints
- Session-based authentication