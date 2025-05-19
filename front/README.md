# Yoga

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0.

## Start the project

Git clone:

> git clone https://github.com/samiraAKO17/Testez-une-application-full-stack.git

Go inside folder:

> cd yoga

Install dependencies:

> npm install

Launch Front-end:

> npm run start;


## Ressources

### Mockoon env 

### Postman collection

For Postman import the collection

> ressources/postman/yoga.postman_collection.json 

by following the documentation: 

https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman


### MySQL

SQL script for creating the schema is available `ressources/sql/script.sql`

By default the admin account is:
- login: yoga@studio.com
- password: test!1234


### Test

## Tests

### Front-end Tests

#### 1. Unit Tests with Jest
- Run unit tests for the front-end:

```bash
npm run test
```

- Run unit tests in watch mode (for active development):

```bash
npm run test:watch
```

- Generate coverage report for Jest:

```bash
npm run test:coverage
```

Coverage report is available here:

> front/coverage/jest/lcov-report/index.html

#### 2. End-to-End Tests with Cypress

- Run E2E tests:

```bash
npm run e2e
```

- Generate coverage report (launch E2E tests first):

```bash
npm run e2e:coverage
```

Report is available here:

> front/coverage/lcov-report/index.html

### Back-end Tests

#### 3. Unit and Integration Tests with JUnit

- Go to the back-end folder:

```bash
cd back
```

- Run tests:

```bash
mvn clean verify
```

- Generate coverage report (Jacoco):

```bash
mvn jacoco:report
```

Report is available here:

> back/target/site/jacoco/index.html