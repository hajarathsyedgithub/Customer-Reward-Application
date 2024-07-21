Customer Rewards Project

Overview

The Customer Rewards Project is a Spring Boot application designed to calculate and manage reward points for customers based on their transactions. The rewards are calculated based on specific rules, where customers earn:

2 points for every dollar spent over $100 in each transaction.

1 point for every dollar spent between $50 and $100 in each transaction.

Features

Calculate Rewards: Calculate rewards points for all customers or a specific customer.

Error Handling: Handles exceptions such as customer not found.

Sample Data: Provides sample customer and transaction data for testing purposes.

API Endpoints

Get Rewards for All Customers
Endpoint: /customers/rewards

Method: GET

Description: Fetches reward points data for all customers, categorized by month and total rewards.

Response Example: { "James Brown": { "JANUARY": 140, "Total Rewards": 140 }, "Michael Phelps": { "JANUARY": 50, "Total Rewards": 50 }, "Robert Williams": { "FEBRUARY": 300, "MARCH": 10, "Total Rewards": 310 }, "Linda Hamilton": { "JANUARY": 200, "FEBRUARY": 300, "MARCH": 400, "Total Rewards": 900 } }

Get Rewards for a Specific Customer
Endpoint: /customers/rewards/{customerId}

Method: GET

Description: Fetches reward points data for a specific customer by their ID.

Path Parameter:

customerId (Integer): The ID of the customer.

Response Example: { "Linda Hamilton": { "JANUARY": 200, "FEBRUARY": 300, "MARCH": 400, "Total Rewards": 900 } }

Error Responses

Customer Not Found:

Status Code: 404 Not Found { "error": "Requested customer id is not found. Customer ID: {customerId}" }

Internal Server Error:

Status Code: 500 Internal Server Error

Response Example: { "error": "An unexpected error occurred." }

Exception Handling

The application includes global exception handling to provide meaningful error messages and avoid exposing sensitive details.

Global Exception Handler

Exception Handled: Exception

Response:

Error Message: "An unexpected error occurred."

Customer Not Found Exception

Exception Handled: CustomerNotFoundException

Response:

Error Message: "Requested customer id is not found. Customer ID: {customerId}"

Getting Started

Prerequisites Java 8 or higher Maven

Installation Clone the repository:

git clone https://github.com/hajarathsyedgithub/customer-reward-application.git

Navigate to the project directory:

cd customer-reward-app

Build the project:

mvn clean install

Running the Application

Start the application:

mvn spring-boot:run

The application will be available at http://localhost:8080.

REST Endpoints GET /customers/rewards): Retrieves the reward points for all customers.

http://localhost:8080/customers/rewards

REST Endpoints GET customers/rewards/customerId: Retrieves the reward points based on specific customer.

http://localhost:8080/customers/rewards/13
