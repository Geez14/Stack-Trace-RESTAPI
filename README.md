# Stack Trace

Welcome to the **Stack Trace**! This application allows users to manage their activities programmatically through a REST API. Users can add new activities to monitor their progress and remove them once completed, ensuring efficient activity management.

---

## Features

- **Add Activities:** Use RESTful endpoints to add activities to your monitoring list.  
- **Delete Activities:** Remove activities from the system once they are completed using REST API calls.  
- **API-Driven Architecture:** Designed for seamless integration into any client application, allowing users to interact programmatically.  

---

## Technical Overview

This application has been developed using modern software development principles to ensure scalability, maintainability, and reliability:

### **1. Built with Spring Boot**
The application is powered by **Spring Boot**, providing a robust framework for creating stand-alone, production-ready RESTful applications.

### **2. Test-First Approach**
Development followed the **Test-First approach**, ensuring that tests were written before the implementation. This methodology enhances the reliability of the application by validating each feature from the outset.

### **3. Steel Thread Approach**
The application was designed and developed using the **Steel Thread approach**, delivering a fully functional core end-to-end system early in the process. This enabled continuous iterative improvements while maintaining a functional core system.

---

## Getting Started

### **Prerequisites**
- Java 17 or later
- Maven 3.8+
- Any API client (e.g., Postman, cURL)

### **Installation**
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-repo/activity-monitor.git
   cd activity-monitor
   ```
2. Build the application:  
   ```bash
   mvn clean install
   ```
3. Run the application:  
   ```bash
   mvn spring-boot:run
   or
   java -jar StackTrace-X.X.X-revision.jar  
   ```

### **API Endpoints**

#### **1. Add an Activity**
**POST** `/api/activities`  
**Body:**  
```json
{
  "title": "Sample Activity",
  "description": "Description of the activity",
}
```

#### **2. Delete an Activity**
**DELETE** `/activities/{id}`  

#### **3. List All Activities**
**GET** `/activities`  

---

## Testing

The application includes a comprehensive suite of tests, including unit and integration tests.  
To run the tests:  
```bash
mvn test
```

---

## Contribution Guidelines

We welcome contributions! Please follow these steps:  
1. Fork the repository.  
2. Create a new branch (`feature/your-feature-name`).  
3. Commit your changes with clear messages.  
4. Open a pull request.
