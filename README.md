# Nissum Technical Challenge

The RESTful API for user creation allows adding new users, following the principles of the REST architecture and using JSON format.

The application uses Spring Boot as the base framework to create a RESTful API and handle the persistence layer. H2 Database is the in-memory database used for development and testing. JSON Web Token (JWT) a token is generated at user creation which must then be validated at authentication. Springfox Swagger is integrated to generate interactive API documentation.

The application includes unit and integration testing using JUnit Jupiter and Mockito to ensure code quality.

## Table of Contents

1. [Technology Stack](#technology-stack)
2. [Configuration](#configuration)
3. [Running from the Console](#running-from-the-console)
4. [Running from IntelliJ IDEA](#running-from-intellij-idea)
5. [Accessing Swagger](#accessing-swagger)
6. [Accessing H2 Database](#accessing-h2-database)
7. [Postman](#postman)
8. [Application Structure](#application-structure)
9. [Testing Endpoints](#testing-endpoints)
10. [Diagrams ](#diagrams)

## Technology stack

- Java 11
- Spring Boot 2.7.14
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Lombok 1.18.20
- H2 Database 2.2.220
- JSON Web Token (JWT) - jjwt 0.11.5
- Springfox Swagger 3.0.0
- JUnit Jupiter 5.8.2
- Mockito 4.0.0

## Configuration

1. Clone this repository on your local machine.
   ```bash
   git clone https://github.com/ValentinaAguirre/nissum-technical-challenge.git
   ```
2. Open a terminal and navigate to the project directory.
   ```bash
   cd nissum-technical-challenge
   ```

## Running from the Console

1. Run the following command to build the project and generate the JAR:
   ```bash
   ./gradlew bootJar

2. Once the construction is finished, navigate to the directory `build/libs`:
   ```bash
   cd build/libs

3. Run the JAR application using the following command:
   ```bash
   java -jar nissumTechicalChallenge-0.0.1-SNAPSHOT.jar

## Running from IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select "File" from the menu bar and then "Open".
3. Navigate to the project directory and select the build.gradle file.
4. Wait for IntelliJ to configure the project.
5. Navigate to the main class UsersApplication.
6. Right-click on the main class and select "Run" to run the application.

## Accessing Swagger

Once the application is running, you can access the Swagger documentation through the following URL in your browser:
 ```bash
  http://localhost:8080/swagger-ui/index.html
 ```

## Accessing H2 Database

You can access the H2 database administration console while the application is running:

1. Navigate to the following URL in your browser:
   ```bash
   http://localhost:8080/h2-console

2. Complete the fields with the following information:

- JDBC URL: `jdbc:h2:mem:NissumTechChallengeDb`
- User Name: `sa`
- Password: (leave this field blank)
- Press the "Connect" button to access the H2 console.

## Postman

Import the Postman collection provided in the doc folder with the name `NissumTechnicalChallenge.postman_collection.json` to test the API endpoints.

## Application Structure

The structure looks as follows :

```
> src
    > main
        > java
            > com.technisys.digital.composite.selfservice
                > configuration
                > controller
                > exception
                > model
                > service
                > utils
        > resources
            application.properties
```
### Controller

```
> controller
    > UserController        
```

### Configuration

```
> configuration
    > SwaggerConfig        
```

### Exception

```
> exception
    > EmailAlreadyRegisteredException
    > GlobalExceptionHandler        
```

### Model

```
    > model
        > dto
            > requests
               > PhoneNumberRequests
               > UserRequests
            > responses
               > UserResponse
        > entity
            > PhoneNumber
            > User

```

### Repository

```
> repository
    > UserRepository      
```

### Services

```
> service    
    > UserService
```

### Utils

```
> utils    
    > JWTGenerator
    > PropertyReader
```

## Testing Endpoints

#### _Enpoint CreateUser_
**_URL:_**  _POST http://localhost:8080/api/users_

**_curl:_**
```
curl -X POST "http://localhost:8080/api/users" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"active\": true, \"email\": \"john@doe.com\", \"name\": \"John Doe\", \"password\": \"Pass123\", \"phones\": [ { \"cityCode\": 4, \"countryCode\": 57, \"number\": 2559433 } ]}"
```
_Input data_
```
{
    "name": "Jhon Doe",
    "email": "john@doe.com",
    "password": "Nissum123",
    "phones": [
        {
            "number": "2559954",
            "cityCode": "4",
            "countryCode": "57"
        },
        {
            "number": "25599546",
            "cityCode": "4",
            "countryCode": "57"
        },
        {
            "number": "2559955",
            "cityCode": "4",
            "countryCode": "57"
        }
    ]
}
```
_Correct output data_
```
Status 200 OK
{
    "id": "237963ee-82c2-4146-99a8-4542c242c816",
    "created": "2023-08-16T10:35:34.483807",
    "modified": "2023-08-16T10:35:34.483807",
    "lastLogin": "2023-08-16T10:35:34.483807",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTY5MjIwMDEzNCwiZXhwIjoxNjkyMjEwOTM0fQ.qmYvdti6cpLgpxpupTY-k_KWLQmzdKftYbb9C3qWVdc",
    "active": true
}

```

_Bad request output data_

```
Status 400 Bad Request
{
    "message": "El correo ya registrado"
}
```

#### _Enpoint GetAllUser_

**_URL:_** GET _http://localhost:8080/api/users_

**_curl:_**
```
curl -X GET "http://localhost:8080/api/users" -H "accept: */*"
```
_Correct output data_
```
Status 200 OK

[
    {
        "id": "70550d07-0ef7-425f-80d1-bb0cc19106a2",
        "name": "Jhon DoePa",
        "email": "john@doe.com",
        "phones": [
            {
                "id": 1,
                "number": "2559954",
                "cityCode": "4",
                "countryCode": "57"
            },
            {
                "id": 2,
                "number": "25599546",
                "cityCode": "4",
                "countryCode": "57"
            },
            {
                "id": 3,
                "number": "2559955",
                "cityCode": "4",
                "countryCode": "57"
            }
        ],
        "created": "2023-08-16T11:38:38.803302",
        "modified": "2023-08-16T11:38:38.803302",
        "lastLogin": "2023-08-16T11:38:38.803302",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJKaG9uIERvZVBhc2FtIiwiaWF0IjoxNjkyMjAzOTE4LCJleHAiOjE2OTIyMTQ3MTh9.iFThXzWgwYs31z0H3COKSHy7Dxl4rTzoOpQfJyOF2eg",
        "active": true
    },
    {
        "id": "cf09c52c-d33c-4e03-8e2b-9359da232765",
        "name": "Jhon DoePasam",
        "email": "john@smith.com",
        "phones": [
            {
                "id": 4,
                "number": "2559954",
                "cityCode": "4",
                "countryCode": "57"
            }
        ],
        "created": "2023-08-16T11:38:58.57001",
        "modified": "2023-08-16T11:38:58.57001",
        "lastLogin": "2023-08-16T11:38:58.57001",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJKaG9uIERvZVBhc2FtIiwiaWF0IjoxNjkyMjAzOTM4LCJleHAiOjE2OTIyMTQ3Mzh9.T9qJXnkMZLOE8tmorcgDSt-sOWh8j8pZExgI1aAqYss",
        "active": true
    }
]
```

_Correct output data no content_
```
Status 204 No Content
```
## Diagrams

**_Solution Diagram_**
![SolutionDiagram.png](doc%2FSolutionDiagram.png)

**_Entity-relationship Diagram_**
![erDiagram.png](doc%2FerDiagram.png)



