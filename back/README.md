# Yoga App !

Ce module correspond à l’API REST développée avec Java 17, Spring Boot 2.7.x, et Maven.

---

## ⚙️ Prérequis

- ✅ Java 17
- ✅ Maven 3+
- ✅ MySQL

---

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

Vérifiez que les identifiants dans application.properties sont corrects.

 ##Lancer le back-end
```bash
cd back 
mvn spring-boot:run 
```
### Tests

#### 3. Unit and Integration Tests with JUnit

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

