# Getting Started
***
### Overview

This service managing the Charging Data Records contracts.It provides to create ,get and search cdr record

### Tech stack overview
* Java 17
* spring-boot-starter-web (Spring boot framework V3.1)
* spring-boot-starter-data-jpa (for persistence)
* spring-boot-starter-actuator (for API statistics)
* postgresql (Database, It runs as a docker image)
* flyway (DB Migration)
* docker compose (Build and run the application)
* TestContainers (provide Database support for integration test)
* spring-boot-starter-test
* springdoc-openapi (API documentation)
* Jacoco (Test Coverage Report)
* Prometheus and Grafana (API Monitoring)

### Technical overview

* Docker compose start the application and database. It uses the same network to run all our services.
* Flyway runs the initial DB script (migration script)
* Please make sure the all required ports are free in the environment like 8081, 5432(mysql), 9090(Prometheus), 3000(Grafana)

### Non-Functional overview
* Grafana allows for the monitoring of application health, performance, and statistics.

### Future Enhancement
* Database optimisation
* Improving search endpoint with pagination and improving sorting.

## Installation
### Pre-requisites

- [Java17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker](https://docs.docker.com/engine/install/)
- [Docker Compose](https://docs.docker.com/compose/)

### Run the application
Run the below commands to start the application via docker

    ./gradlew clean build
    docker-compose up -d

### How do we access the service?
We can access the application from the [swagger ui](http://localhost:8081/swagger-ui/index.html)


### Build and run the app

To create the jar file

    ./gradlew clean build

To run the unit test

    ./gradlew test
Html Test report : <ProjectRoot>build/reports/tests/test/index.html  

To run the integration test

    ./gradlew integrationTest
Html Test report : <ProjectRoot>/build/reports/tests/integrationTest/index.html

To Generate Jacoco Test coverage Report

    ./gradlew applicationCodeCoverageReport
Html Test coverage report : <ProjectRoot>build/reports/jacoco/applicationCodeCoverageReport/html/index.html

#### Data configuration
* Login into [Grafana](http://localhost:3000/)

  Username: admin  
  Password: admin

* Goto Data Source and add our Prometheus as data source
* Add Prometheus URL (http://prometheus:9090)
 
