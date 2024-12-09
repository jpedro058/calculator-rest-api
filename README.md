
# Calculator REST API with Apache Kafka Integration

This project is a RESTful API that performs basic calculator operations (sum, subtraction, multiplication, division) with support for arbitrary precision signed decimal numbers. It uses Spring Boot for the backend and Apache Kafka for inter-module communication.

### Project Structure

The project consists of two main modules:
- **calculator**: Handles the core calculator logic and Kafka consumer.
- **rest**: Provides the REST API, integrates Kafka producer, and exposes the calculator functionality.

### Technologies Used
- **Spring Boot**: For building the REST API and Kafka integration.
- **Apache Kafka**: For communication between the `rest` and `calculator` modules.
- **Docker**: To containerize the application and provide an easy setup for running the services.
- **Maven**: For building and managing dependencies.
- **JUnit**: For unit testing.

---  

## Prerequisites

- Docker and Docker Compose installed on your machine.
- Java 21 installed on your machine.
- Maven installed to build the project (if you are not using Docker to build the project).
- Kafka running locally or through Docker (configured in the project).

---  

## Setup Instructions

### Step 1: Clone the Repository

 ```bash  
git clone https://github.com/your-username/calculator-rest-api.git  
```  

```bash  
cd calculator-rest-api  
```  
### Step 2: Build the Project Using Maven

 ```bash 
 mvn clean install 
 ```  
### Step 3:  Run the Application Using Docker Composer

 ```bash 
 docker-compose up -d 
 ```  
### Step 4: Run the Application Using Maven

 ```bash 
 cd rest
 ```  
  ```bash 
mvn spring-boot:run 
 ```  
 
---
## API Endpoints
The REST API provides the following endpoints:

Basic Calculator Operations:
### Sum:
`GET /api/sum?a={a}&b={b}`  
Example:
```bash  
curl -X GET "http://localhost:8080/api/sum?a=10&b=20"  
```  
### Subtraction:
`GET /api/subtract?a={a}&b={b}`  
Example:
```bash  
curl -X GET "http://localhost:8080/api/subtract?a=10&b=20"  
```  
### Multiplication:
`GET /api/multiply?a={a}&b={b}`  
Example:
```bash  
curl -X GET "http://localhost:8080/api/multiply?a=10&b=20"  
```  
### Division:
`GET /api/divide?a={a}&b={b}`  
Example:
```bash  
curl -X GET "http://localhost:8080/api/divide?a=10&b=20"  
```  
Division by zero will return an error response:
```bash  
curl -X GET "http://localhost:8080/api/divide?a=10&b=0"  
```  
---
## Kafka Integration
The `rest` module sends operation requests to the `calculator` module using Apache Kafka. The `calculator` module listens to the Kafka topic and processes the requests.

### Send Addition Request via Kafka:
`GET /send?operation=add&a={a}&b={b}`  
Example:
```bash  
curl "http://localhost:8080/send?operation=add&a=10&b=20"  
```  
### Send Subtraction Request via Kafka:
`GET /send?operation=subtract?a={a}&b={b}`  
Example:
```bash  
curl "http://localhost:8080/send?operation=subtract&a=10&b=20"  
```  
### Send Multiplication Request via Kafka:
`GET /send?operation=multiply?a={a}&b={b}`  
Example:
```bash  
curl "http://localhost:8080/send?operation=multiply&a=10&b=20"  
```  
### Send Division Request via Kafka:
`GET /send?operation=divide?a={a}&b={b}`  
Example:
```bash  
curl "http://localhost:8080/send?operation=divide&a=10&b=20"  
```  
Division by zero will return an error response:
```bash 
curl "http://localhost:8080/send?operation=divide&a=10&b=0"  
``` 
---  

## Running Tests

To run the unit tests, use the following command:

 ```bash 
 mvn test 
 ```
 ---  

## Stopping the Application

To stop the application, use the following command:

 ```bash 
 docker-compose down 
 ```