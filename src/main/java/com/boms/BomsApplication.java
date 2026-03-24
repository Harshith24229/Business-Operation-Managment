package com.boms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BomsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BomsApplication.class, args);
    }
}
/*
* ================================================================
   HOSPITAL MANAGEMENT SYSTEM - COMPLETE API REFERENCE
   Base URL: http://localhost:8080
   All protected routes need: Authorization: Bearer <TOKEN>
================================================================

================================================================
STEP 1: REGISTER USERS (No Auth Required)
================================================================

--- Register a PATIENT ---
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Password@123",
  "phone": "9876543210",
  "role": "PATIENT",
  "gender": "MALE",
  "dateOfBirth": "1990-05-15",
  "bloodGroup": "O+",
  "address": "123 MG Road",
  "city": "Bengaluru",
  "state": "Karnataka",
  "zipCode": "560001",
  "country": "India"
}

----------------------------------------------------------------

--- Register a DOCTOR ---
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "firstName": "Sarah",
  "lastName": "Smith",
  "email": "dr.sarah@hospital.com",
  "password": "Doctor@123",
  "phone": "9876543211",
  "role": "DOCTOR",
  "gender": "FEMALE",
  "specialization": "Cardiology",
  "licenseNumber": "MED-12345",
  "department": "Cardiology",
  "address": "456 Hospital Road",
  "city": "Bengaluru",
  "state": "Karnataka",
  "zipCode": "560002",
  "country": "India"
}

----------------------------------------------------------------

--- Register a CASHIER ---
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "firstName": "Ravi",
  "lastName": "Kumar",
  "email": "ravi.cashier@hospital.com",
  "password": "Cashier@123",
  "phone": "9876543212",
  "role": "CASHIER",
  "gender": "MALE",
  "address": "789 Staff Quarters",
  "city": "Bengaluru",
  "state": "Karnataka",
  "zipCode": "560003",
  "country": "India"
}

----------------------------------------------------------------

--- Register an ADMIN ---
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "firstName": "Admin",
  "lastName": "User",
  "email": "admin@hospital.com",
  "password": "Admin@123",
  "phone": "9876543213",
  "role": "ADMIN",
  "gender": "MALE"
}


================================================================
STEP 2: LOGIN (No Auth Required)
================================================================

--- Login ---
POST http://localhost:8080/api/users/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "Password@123"
}

RESPONSE will contain:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "john.doe@example.com",
  "role": "PATIENT",
  "firstName": "John",
  "lastName": "Doe"
}

NOTE: Copy the "token" value — use it in all subsequent requests
as:  Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

----------------------------------------------------------------

--- Validate Token ---
GET http://localhost:8080/api/users/validate-token?token=<YOUR_TOKEN>


================================================================
SECTION 1: USER SERVICE APIs
================================================================

--- Get User by ID ---
GET http://localhost:8080/api/users/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get User by Email ---
GET http://localhost:8080/api/users/email/john.doe@example.com
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get All Active Doctors ---
GET http://localhost:8080/api/users/doctors/active
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get Users by Role (PATIENT / DOCTOR / CASHIER / ADMIN) ---
GET http://localhost:8080/api/users/role/PATIENT
Authorization: Bearer <TOKEN>

GET http://localhost:8080/api/users/role/DOCTOR
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Update User Profile ---
PUT http://localhost:8080/api/users/1
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "9876543299",
  "address": "999 New Street",
  "city": "Mumbai",
  "state": "Maharashtra",
  "zipCode": "400001",
  "country": "India",
  "gender": "MALE",
  "dateOfBirth": "1990-05-15",
  "bloodGroup": "O+"
}

----------------------------------------------------------------

--- Deactivate User (Admin Only) ---
DELETE http://localhost:8080/api/users/admin/1
Authorization: Bearer <ADMIN_TOKEN>


================================================================
SECTION 2: APPOINTMENT SERVICE APIs
================================================================

--- Book an Appointment ---
POST http://localhost:8080/api/appointments
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "patientEmail": "john.doe@example.com",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "doctorEmail": "dr.sarah@hospital.com",
  "specialization": "Cardiology",
  "department": "Cardiology",
  "appointmentDate": "2026-04-10",
  "appointmentTime": "10:00:00",
  "type": "CONSULTATION",
  "reason": "Chest pain follow-up",
  "notes": "Patient has history of hypertension"
}

NOTE - Appointment Types:
  CONSULTATION, FOLLOW_UP, EMERGENCY, ROUTINE_CHECKUP, PROCEDURE

----------------------------------------------------------------

--- Book Follow-Up Appointment ---
POST http://localhost:8080/api/appointments
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "patientEmail": "john.doe@example.com",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "doctorEmail": "dr.sarah@hospital.com",
  "specialization": "Cardiology",
  "department": "Cardiology",
  "appointmentDate": "2026-04-20",
  "appointmentTime": "11:30:00",
  "type": "FOLLOW_UP",
  "reason": "Blood pressure review after medication"
}

----------------------------------------------------------------

--- Get Appointment by ID ---
GET http://localhost:8080/api/appointments/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get All Appointments for a Patient ---
GET http://localhost:8080/api/appointments/patient/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get All Appointments for a Doctor ---
GET http://localhost:8080/api/appointments/doctor/2
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get Doctor Appointments by Date ---
GET http://localhost:8080/api/appointments/doctor/2/date/2026-04-10
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Confirm an Appointment ---
PUT http://localhost:8080/api/appointments/1/confirm
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Cancel an Appointment ---
PUT http://localhost:8080/api/appointments/1/cancel?reason=Patient%20requested%20cancellation
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Complete an Appointment ---
PUT http://localhost:8080/api/appointments/1/complete
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Reschedule an Appointment ---
PUT http://localhost:8080/api/appointments/1/reschedule?newDate=2026-04-15&newTime=14:00:00
Authorization: Bearer <TOKEN>


================================================================
SECTION 3: MEDICAL RECORDS SERVICE APIs
================================================================

--- Create a Prescription ---
POST http://localhost:8080/api/medical/prescriptions
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "appointmentId": 1,
  "diagnosis": "Hypertension - Stage 1",
  "notes": "Patient should monitor blood pressure daily. Reduce salt intake. Exercise 30 minutes daily.",
  "validUntil": "2026-06-30",
  "medicines": [
    {
      "name": "Amlodipine",
      "dosage": "5mg",
      "frequency": "Once daily",
      "duration": "30 days",
      "instructions": "Take in the morning with water"
    },
    {
      "name": "Aspirin",
      "dosage": "75mg",
      "frequency": "Once daily",
      "duration": "30 days",
      "instructions": "Take after food"
    },
    {
      "name": "Atorvastatin",
      "dosage": "10mg",
      "frequency": "Once daily at night",
      "duration": "30 days",
      "instructions": "Take at bedtime"
    }
  ]
}

----------------------------------------------------------------

--- Create Prescription with Single Medicine ---
POST http://localhost:8080/api/medical/prescriptions
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "diagnosis": "Common Cold",
  "notes": "Rest and drink plenty of fluids",
  "validUntil": "2026-04-15",
  "medicines": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "frequency": "Thrice daily",
      "duration": "5 days",
      "instructions": "Take after meals"
    }
  ]
}

----------------------------------------------------------------

--- Get Prescription by ID ---
GET http://localhost:8080/api/medical/prescriptions/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get All Prescriptions for a Patient ---
GET http://localhost:8080/api/medical/prescriptions/patient/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Update Prescription Status ---
PUT http://localhost:8080/api/medical/prescriptions/1/status?status=COMPLETED
Authorization: Bearer <TOKEN>

NOTE - Prescription Status values:
  ACTIVE, COMPLETED, EXPIRED, CANCELLED

----------------------------------------------------------------

--- Order a Medical Test ---
POST http://localhost:8080/api/medical/tests
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "appointmentId": 1,
  "testName": "Complete Blood Count (CBC)",
  "testType": "BLOOD_TEST",
  "lab": "Central Diagnostic Lab"
}

----------------------------------------------------------------

--- Order Multiple Tests (run separately for each) ---

POST http://localhost:8080/api/medical/tests
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "testName": "Lipid Profile",
  "testType": "BLOOD_TEST",
  "lab": "Central Diagnostic Lab"
}

----------------------------------------------------------------

POST http://localhost:8080/api/medical/tests
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "testName": "ECG (Electrocardiogram)",
  "testType": "CARDIAC_TEST",
  "lab": "Cardiology Department"
}

----------------------------------------------------------------

POST http://localhost:8080/api/medical/tests
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "testName": "Chest X-Ray",
  "testType": "RADIOLOGY",
  "lab": "Radiology Department"
}

----------------------------------------------------------------

--- Get Test by ID ---
GET http://localhost:8080/api/medical/tests/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get All Tests for a Patient ---
GET http://localhost:8080/api/medical/tests/patient/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Update Test Status ---
PUT http://localhost:8080/api/medical/tests/1/status?status=SAMPLE_COLLECTED
Authorization: Bearer <TOKEN>

PUT http://localhost:8080/api/medical/tests/1/status?status=PROCESSING
Authorization: Bearer <TOKEN>

NOTE - Test Status values:
  ORDERED, SAMPLE_COLLECTED, PROCESSING, COMPLETED, CANCELLED

----------------------------------------------------------------

--- Upload NORMAL Test Result ---
PUT http://localhost:8080/api/medical/tests/1/result?result=WBC%3A%207.2%2C%20RBC%3A%204.8%2C%20Hemoglobin%3A%2014.2%20-%20All%20within%20normal%20range&fileUrl=https://hospital.com/reports/cbc-001.pdf&abnormal=false&remarks=All%20parameters%20within%20normal%20limits
Authorization: Bearer <TOKEN>

(Decoded for readability:)
  result  = WBC: 7.2, RBC: 4.8, Hemoglobin: 14.2 - All within normal range
  fileUrl = https://hospital.com/reports/cbc-001.pdf
  abnormal= false
  remarks = All parameters within normal limits

----------------------------------------------------------------

--- Upload ABNORMAL Test Result (triggers priority email) ---
PUT http://localhost:8080/api/medical/tests/2/result?result=Blood%20Sugar%20Fasting%3A%20145%20mg%2FdL%20-%20Above%20normal%20range&fileUrl=https://hospital.com/reports/sugar-001.pdf&abnormal=true&remarks=Fasting%20glucose%20elevated.%20Immediate%20follow%20up%20required.
Authorization: Bearer <TOKEN>

(Decoded for readability:)
  result  = Blood Sugar Fasting: 145 mg/dL - Above normal range
  fileUrl = https://hospital.com/reports/sugar-001.pdf
  abnormal= true
  remarks = Fasting glucose elevated. Immediate follow up required.


================================================================
SECTION 4: BILLING SERVICE APIs
================================================================

--- Generate a Bill ---
POST http://localhost:8080/api/billing/bills
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "patientEmail": "john.doe@example.com",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "appointmentId": 1,
  "items": [
    {
      "description": "Cardiology Consultation",
      "type": "CONSULTATION",
      "quantity": 1,
      "unitPrice": 800.00
    },
    {
      "description": "ECG Test",
      "type": "TEST",
      "quantity": 1,
      "unitPrice": 600.00
    },
    {
      "description": "Complete Blood Count",
      "type": "TEST",
      "quantity": 1,
      "unitPrice": 400.00
    }
  ],
  "discount": 100.00,
  "tax": 135.00,
  "notes": "Follow-up visit",
  "dueDate": "2026-04-20"
}

NOTE - Bill Item Types:
  CONSULTATION, PROCEDURE, TEST, MEDICINE, ROOM_CHARGE, MISC

----------------------------------------------------------------

--- Generate Bill with Insurance ---
POST http://localhost:8080/api/billing/bills
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "patientId": 1,
  "patientName": "John Doe",
  "patientEmail": "john.doe@example.com",
  "doctorId": 2,
  "doctorName": "Sarah Smith",
  "items": [
    {
      "description": "Cardiology Procedure",
      "type": "PROCEDURE",
      "quantity": 1,
      "unitPrice": 5000.00
    },
    {
      "description": "Hospital Room - 2 days",
      "type": "ROOM_CHARGE",
      "quantity": 2,
      "unitPrice": 2000.00
    },
    {
      "description": "Medicines",
      "type": "MEDICINE",
      "quantity": 1,
      "unitPrice": 1500.00
    }
  ],
  "discount": 500.00,
  "tax": 850.00,
  "insuranceProvider": "Star Health Insurance",
  "insurancePolicyNumber": "SH-2024-98765",
  "dueDate": "2026-04-30"
}

----------------------------------------------------------------

--- Get Bill by ID ---
GET http://localhost:8080/api/billing/bills/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get All Bills for a Patient ---
GET http://localhost:8080/api/billing/bills/patient/1
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Record Full Payment (CASH) ---
POST http://localhost:8080/api/billing/bills/1/payment?amount=1835.00&paymentMethod=CASH
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Record Full Payment (UPI) ---
POST http://localhost:8080/api/billing/bills/1/payment?amount=1835.00&paymentMethod=UPI&transactionId=UPI20260410123456
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Record Partial Payment (CARD) ---
POST http://localhost:8080/api/billing/bills/1/payment?amount=1000.00&paymentMethod=CARD&transactionId=CARD20260410789012
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Record Insurance Payment ---
POST http://localhost:8080/api/billing/bills/1/payment?amount=8000.00&paymentMethod=INSURANCE&transactionId=INS-CLAIM-2026-001
Authorization: Bearer <TOKEN>

NOTE - Payment Methods:
  CASH, CARD, UPI, INSURANCE, BANK_TRANSFER

----------------------------------------------------------------

--- Get Payment History for a Bill ---
GET http://localhost:8080/api/billing/bills/1/payments
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Cancel a Bill ---
PUT http://localhost:8080/api/billing/bills/1/cancel?reason=Duplicate%20bill%20generated
Authorization: Bearer <TOKEN>


================================================================
SECTION 5: NOTIFICATION SERVICE APIs
================================================================

--- Get All Notifications for a Recipient ---
GET http://localhost:8080/api/notifications/recipient/john.doe@example.com
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get Notifications by Status ---
GET http://localhost:8080/api/notifications/status/SENT
Authorization: Bearer <TOKEN>

GET http://localhost:8080/api/notifications/status/FAILED
Authorization: Bearer <TOKEN>

GET http://localhost:8080/api/notifications/status/PENDING
Authorization: Bearer <TOKEN>

NOTE - Notification Status values:
  PENDING, SENT, FAILED, SKIPPED

----------------------------------------------------------------

--- Get Notifications by Event Type ---
GET http://localhost:8080/api/notifications/event/USER_REGISTERED
Authorization: Bearer <TOKEN>

GET http://localhost:8080/api/notifications/event/APPOINTMENT_BOOKED
Authorization: Bearer <TOKEN>

GET http://localhost:8080/api/notifications/event/BILL_GENERATED
Authorization: Bearer <TOKEN>

----------------------------------------------------------------

--- Get Notification by ID ---
GET http://localhost:8080/api/notifications/1
Authorization: Bearer <TOKEN>


================================================================
SECTION 6: SWAGGER UI (Open in Browser - No Auth Needed)
================================================================

Eureka Dashboard  : http://localhost:8761
API Gateway       : http://localhost:8080/actuator
User Service      : http://localhost:8081/swagger-ui.html
Appointment Svc   : http://localhost:8082/swagger-ui.html
Medical Records   : http://localhost:8083/swagger-ui.html
Billing Service   : http://localhost:8084/swagger-ui.html
Notification Svc  : http://localhost:8085/swagger-ui.html


================================================================
SECTION 7: VALIDATION RULES QUICK REFERENCE
================================================================

PASSWORD must have ALL of:
  - Minimum 8 characters
  - At least 1 uppercase letter (A-Z)
  - At least 1 lowercase letter (a-z)
  - At least 1 digit (0-9)
  - At least 1 special character (!@#$%^&*)
  Example: Password@123  |  Doctor@123  |  Admin@2024!

PHONE: 10-15 digits only, must be unique
  Example: 9876543210  |  +919876543210

EMAIL: valid format, must be unique
  Example: john@example.com

ROLE: exactly one of:
  PATIENT  |  DOCTOR  |  CASHIER  |  ADMIN

BLOOD GROUP: A+  A-  B+  B-  AB+  AB-  O+  O-

GENDER: MALE  |  FEMALE  |  OTHER

DATE FORMAT: YYYY-MM-DD
  Example: 1990-05-15  |  2026-04-10

TIME FORMAT: HH:mm:ss
  Example: 10:00:00  |  14:30:00  |  09:15:00

DOCTOR-ONLY required fields:
  specialization  (e.g. Cardiology, Orthopedics, Neurology)
  licenseNumber   (e.g. MED-12345)


================================================================
SECTION 8: KAFKA EVENTS QUICK REFERENCE
================================================================

Topic: user-events
  USER_REGISTERED   → Welcome email to patient/doctor
  PROFILE_UPDATED   → Profile change confirmation email

Topic: appointment-events
  APPOINTMENT_BOOKED      → Email to patient + doctor
  APPOINTMENT_CONFIRMED   → Confirmation email to patient
  APPOINTMENT_CANCELLED   → Cancellation email to patient
  APPOINTMENT_RESCHEDULED → Reschedule email to patient
  APPOINTMENT_COMPLETED   → Feedback request email to patient

Topic: medical-events
  PRESCRIPTION_CREATED    → New prescription email to patient
  TEST_ORDERED            → Lab test order email to patient
  TEST_RESULT_UPLOADED    → Result ready email (URGENT if abnormal)

Topic: billing-events
  BILL_GENERATED    → Invoice email to patient
  PAYMENT_RECEIVED  → Payment receipt email to patient
  PAYMENT_OVERDUE   → Overdue reminder email to patient
  BILL_CANCELLED    → Cancellation email to patient


================================================================
SECTION 9: COMPLETE WORKFLOW EXAMPLE (End-to-End)
================================================================

STEP 1: Register Doctor
  POST /api/users/register  (role: DOCTOR with specialization)

STEP 2: Register Patient
  POST /api/users/register  (role: PATIENT)

STEP 3: Login as Patient
  POST /api/users/login  → save the token

STEP 4: Book Appointment
  POST /api/appointments  (use patient + doctor IDs from steps 1-2)

STEP 5: Login as Doctor, Confirm Appointment
  PUT /api/appointments/1/confirm

STEP 6: Doctor Creates Prescription
  POST /api/medical/prescriptions

STEP 7: Doctor Orders Tests
  POST /api/medical/tests

STEP 8: Lab Updates Test Status
  PUT /api/medical/tests/1/status?status=SAMPLE_COLLECTED
  PUT /api/medical/tests/1/status?status=PROCESSING

STEP 9: Lab Uploads Result
  PUT /api/medical/tests/1/result?result=...&abnormal=false

STEP 10: Complete Appointment
  PUT /api/appointments/1/complete

STEP 11: Login as Cashier, Generate Bill
  POST /api/billing/bills

STEP 12: Record Payment
  POST /api/billing/bills/1/payment?amount=...&paymentMethod=CASH

STEP 13: Check Notifications Sent
  GET /api/notifications/recipient/john.doe@example.com

================================================================
END OF API REFERENCE
================================================================
*/