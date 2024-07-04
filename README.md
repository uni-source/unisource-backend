# UniSource - University-Driven Open Source Community

## Overview

UniSource is a platform designed to bridge the gap between university students and the software industry by facilitating student contributions to open-source projects managed by industry organizations. This repository contains the backend part of the project, implemented using Spring Boot and following a microservices architecture.

## Global Architecture Diagram

Below is the global architecture diagram of the UniSource platform:

![Global Architecture Diagram](architecture_diagram.svg)

## Microservices

This backend project is divided into multiple microservices:

1. **Identity Service**: Manages user authentication and authorization.
2. **Admin Service**: Handles administrative tasks, including verifying student and organization accounts.
3. **Organization Service**: Manages organization registrations, project submissions, and mentor assignments.
4. **Student Service**: Manages student registrations, profiles, and proposals for projects.
5. **Project Service**: Handles the lifecycle of projects, including submissions, reviews, and management.

Each microservice is implemented as a separate Spring Boot application and communicates with others through REST APIs.

## Getting Started

### Prerequisites

- Java 21
- Maven
- MySQL

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/reshangayantha9/unisource-backend.git
    cd unisource-backend
    ```
2. Configure the database in the config server application.yaml file.

3. Build the project:
    ```sh
    mvn clean install
    ```

## Running the Services

To run each microservice, navigate to its directory and use the following command:
```sh
mvn spring-boot:run
```
### Technologies Used

- Spring Boot: For building the backend services.
- Spring Cloud: For handling microservices communication and configuration.
- MySQL: As the relational database.
- JWT: For authentication and authorization.
    

### Development

Code Structure
Each microservice has its own directory with the following structure:

```
/identity-service
    /src
    /main
    /java
    ...
/admin-service
    /src
    /main
    /java
    ...
/organization-service
    /src
    /main
    /java
    ...
/student-service
    /src
    /main
    /java
    ...
/project-service
    /src
    /main
    /java
    ...
```

### Configuration
- Each microservice has its own application.yaml file for configurations. Common configurations can be managed using Spring Cloud Config.

### Contributing
Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -m 'Add some feature').
4. Push to the branch (git push origin feature-branch).
5. Open a pull request.

### License
This project is licensed under the MIT License. See the LICENSE file for details.