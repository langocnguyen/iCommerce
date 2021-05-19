# iCommerce Project
An online shopping application to sell products (backend only).

## Requirements
The application is simply a simple web page that shows all products which customers can filter, sort and search for products based on different criteria such as name, price, brand, color etc. 

Customer is also able to order product(s) that he/she want to buy and needs to input the full name, phone number, address and email to submit an order (No Facebook login is supported yet). 

Administrator only can add, update or delete product. 

## How to run the application ?
### Setup development workspace
1. Install JDK version 8.
2. Install Maven.
3. Clone this project to your local machine. 

### Run application
> Make sure you are in the root directory of project you want to run

Run Spring Boot command: 
```sh
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
> Note that all requests need JWT Access Token (by adding "Authorization" to HTTP Header with value "Bearer <JWT Access Token>").
### Token 
You can get JWT token by this API:
```sh
curl -L -X POST 'http://localhost:8080/api/v1/login' -H 'Content-Type: application/json' --data-raw '{
    "username": "admin",
    "password": "admin"
}'
```
> Modify `username` and `password` of valid user. 
> Since this project using H2 database, data will be cleared after restarting. For testing, I added two users with following information when start application:
> 1. Admin user
>
> | Username| Password|
> | ------ | ------ |
> | admin| admin |
> 2. Customer user
>
> | Username| Password|
> | ------ | ------ |
> | customer | customer |

### Product 
#### 1. Get all products
```sh
curl -L -X GET 'http://localhost:8080/api/v1/products' \
-H 'Authorization: Bearer <JWT Token>'
```

#### 2. Get product with filter and sort
```sh
curl -L -X GET 'http://localhost:8080/api/v1/products?amount=2&name=Product%201&color=blue&price=12333&order=DESC&order_by=price' \
-H 'Authorization: Bearer <JWT Token>'
```

#### 2. Search a product by id
```sh
curl -L -X GET 'http://localhost:8080/api/v1/products/{id}' \
-H 'Authorization: Bearer <JWT Token>'
```

#### 3. Add a product (Admin only)
```sh
curl -L -X POST 'http://localhost:8080/api/v1/products' \
-H 'Authorization: Bearer <JWT token>' \
-H 'Content-Type: application/json' \
--data-raw '{
    "name":"Product 1",
    "price": 100000,
    "brand":"Brand 1",
    "color": "blue",
    "quantity": 5
}'
```
#### 4. Update product information (Admin only)
```sh
curl -L -X PUT 'http://localhost:8080/api/v1/products/{id}' \
-H 'Authorization: Bearer <JWT Token>' \
-H 'Content-Type: application/json' \
--data-raw '{
    "name":"Updated name",
    "price": 150000,
    "brand": "Updated name",
    "color": "red",
    "quantity": 40
}'
```
#### 5. Delete a product (Admin only): 
```sh
curl -L -X DELETE 'http://localhost:8080/api/v1/products/{id}' \
-H 'Authorization: Bearer <JWT Token>' 
```

### Order 
#### 1. Order a product
```sh
curl -L -X POST 'http://localhost:8080/api/v1/orders' \
-H 'Authorization: Bearer <JWT Token>' \
-H 'Content-Type: application/json' \
--data-raw '{
    "full_name": "Jon Henrry",
    "phone_number": "+84907345112",
    "address": "23 Dis LA USA",
    "email": "example@mail.com",
    "order_details": [
        {
            "product_id": 4,
            "quantity": 2
        }
    ]
}'
```

#### 2. Get orders

```sh
curl -L -X GET 'http://localhost:8080/api/v1/orders' \
-H 'Authorization: Bearer <JWT Token>'
```



