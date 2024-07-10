# Lottery System

This project is a simple lottery system built with Spring Boot. It allows you to create, amend, and check the status of lottery tickets. The project includes proper exception handling and unit tests.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: Install JDK 11.
- **Maven**: Ensure Maven is installed on your machine. You can download it from [here](https://maven.apache.org/download.cgi).
- **Git**: Install Git to clone the repository. You can download it from [here](https://git-scm.com/downloads).

## Getting Started

To get a local copy of the project up and running, follow these steps:

### 1. Clone the Repository

```bash
git clone https://github.com/abhranil/lottery-system.git
cd lottery-system
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

java -jar target/lottery-system-0.0.1-SNAPSHOT.jar

### 4. Access the Application
Once the application is running, you can access it at http://localhost:8080.

### 5. Running Tests

To run the tests, use the following Maven command:
```bash
mvn test
```
This will run all unit tests defined in the project.

### 6. API Endpoints

The following endpoints are available in the application:

- **Create Ticket**

    POST /tickets
    - Request Body: { "lines": <int> }
    - Response: 201 Created with the created ticket details
- **Get All Tickets**
    GET /tickets
    - Response: 200 OK with the list of tickets
- **Amend Ticket**
    PUT /tickets/{id}/amend 
    - Request Body: { "lines": <int> }
    - Response: 200 OK with the amended ticket details
- **Check Ticket Status**
    GET /tickets/{id}/status
    - Response: 200 OK with the ticket status

### 7. Exception Handling

The application handles the following exceptions:

- **TicketNotFoundException** - Thrown when a ticket is not found
- **CannotAmendCheckedTicketException** - Thrown when an attempt is made to amend a checked ticket

- These exceptions are handled globally by GlobalExceptionHandler.

### 8. Testing using Postman

Import the **Lottery API.postman_collection.json** from .local folder into Postman and hit the respective endpoints to test the application.

