# iCommerce Project
An online shopping application to sell products (backend only).

## Requirements
The application is simply a simple web page that shows all products which customers can filter, sort and search for products based on different criteria such as name, price, brand, color etc. 
Customer is also able to order product(s) that he/she want to buy and needs to input the full name, phone number, address and email to submit an order (No Facebook login is supported yet). 

Only administrator can add, update or delete product. 

## How to run the application ?

### Setup development workspace
1. Install JDK version 8.
2. Install Maven.
3. Clone this project to your local machine. 

### Run application

** Make sure you are in the root directory of project you want to run

Run Spring Boot command: 
```bash 
mvn spring-boot:run
```

## Project folder structure and Frameworks, Libraries
### Project folder structure
The folder structure is design based on following layers:
- Controller: Handle HTTP request from client, invoke appropriate methods in service layer, return the result to client.
- Service: all business logic here. Data related calculations and all.
- Repository: all the Database related operations are done here.
- Entity: persistent domain object - table in Databases.
- Dto: used for transfer data. 
- Utils: includes common utility functions such as Email validation... 
- Exception: exception handling. 

### Frameworks and Libraries 
- spring-boot-starter-web : for building REST API.
- spring-boot-starter-test : Starter for testing Spring Boot applications with libraries including JUnit and Mockito.
- spring-boot-starter-data-jpa: for using Spring Data JPA with Hibernate.
- spring-boot-starter-validation: for using Java Bean Validation with Hibernate Validator.
- spring-boot-starter-security: for using Spring Security.
- h2: for using H2 in-memory database. 
- commons-lang3: for using utility methods to handle logic with value (StringUtils, CollectionUtils,...).
- libphonenumber: phone number validation purpose. 

## CRUD commands
Note that all requests need JWT Access Token (by adding "Authorization" to HTTP Header with value "Bearer <JWT Access Token>").
### Get token 
### Product 
1. Get product:

2. Add a product (for Admin only):

3. Update a product (for Admin only):

4. Delete a product (for Admin only): 

### Order 
1. Order a product without filer:

2. Order a product with filer:








