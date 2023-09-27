# Spring API Bank demo

Spring boot banking API demo.

## Prerequisites

Before you begin, ensure you have met the following requirements:

* Java Development Kit
* Apache Maven
* Your preferred IDE

## Getting Started

Clone the Repository: https://github.com/Nix4NixSD/spring-demo

* HTTPS: https://github.com/Nix4NixSD/spring-demo.git
* SSH: git@github.com:Nix4NixSD/spring-demo.git
* GitHub CLI: gh repo clone Nix4NixSD/spring-demo

### Build and Run

-**Using Command Line**-

`mvn clean install`

`mvn spring-boot:run`

-**Using IDE**-

In your IDE, find the main class (usually annotated with @SpringBootApplication) and run it. This will start the Spring Boot application.

### Units test

To run unit tests, you can use Maven. Run the following command in your project directory:

`mvn test`

Or use your IDE, find the main Test class and run it. 


## Api endpoints

Endpoints that are implemented:

**"/api/customer"**

* "/get/{id}" : returns a customer with the given ID.
* "/get" : returns all customers in the database.
* "/create" : creates a new customer with the given json.
* "/update" : updates the given customer.

**"/api/account"**

* "/create" : creates a new account using the supplied json. 
* "/get" : returns all accounts and their transactions. 
* "/get/{ownerId}" : tries to get a specific account and it's transactions

H2 database url: http://localhost:8080/h2-console
Just press login on the H2 panel to access the H2 database.