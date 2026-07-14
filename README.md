# Rest_Assured_API_Testing

## Overview
This project contains automated API tests for the Books API written in Java, using Rest Assured for HTTP request/response testing and JUnit 5 for test execution. Tests are built with Maven. The suite covers happy path scenarios, edge cases, and error conditions for all four HTTP methods (GET, POST, PUT) supported by the Books API.

## Libraries (from pom.xml)
- Java 21 (maven.compiler.source/target = 21)
- io.rest-assured:rest-assured:5.5.5
- io.rest-assured:json-schema-validator:5.5.0 (test scope)
- org.junit.jupiter:junit-jupiter-api:5.13.2 (test scope)
- org.junit.jupiter:junit-jupiter-params:5.13.2 (test scope)
- org.projectlombok:lombok:1.18.38

## Test Cases Addressed
Below are the test titles extracted from @DisplayName annotations found in the test sources under src/test/java. They are grouped by endpoint and presented verbatim.

- GET /Books
  - "GET /Books without body returns 200 and valid schema"
  - "GET /Books with irrelevant body returns 200 and valid schema"
  - "GET /Books with irrelevant query parameters returns 200 and valid schema"
  - "GET /Books with bearer token returns 200 and valid schema"

- GET /Books/{id}
  - "GET /Books/{id} with edge (1,200) and middle (100) valid IDs returns 200 and valid schema"
  - "GET /Books/{id} with edge (0,201) non valid IDs returns 404"
  - "GET /Books/{id} with equal to max int32 non valid IDs returns 404"
  - "GET /Books/{id} with beyond max int32 non valid IDs returns 400"
  - "GET /Books/{id} with non integer path ID returns 400"
  - "GET /Books/{id} with quoted numeric valid IDs returns 200 and valid schema"
  - "GET /Books/{id} with irrelevant body returns 200 and valid schema"
  - "GET /Books/{id} with query parameters returns 200 and valid schema"
  - "GET /Books/{id} with bearer token returns 200 and valid schema"

- POST /Books
  - "POST /Books with empty request body returns 200 and valid schema"
  - "POST /Books with valid request body returns 200 and valid schema"
  - "POST /Books with id as string returns 200 and valid schema"
  - "POST /Books with pagecount as string returns 200 and valid schema"
  - "POST /Books with missing one or more key value pairs returns 200 and valid schema"
  - "POST /Books with null publishDate returns 400"
  - "POST /Books with invalid date format for publishDate returns 400"
  - "POST /Books with text fields set to null returns 200 and valid schema"
  - "POST /Books with id and/or pagecount set to null returns 400"
  - "POST /Books with extra body field returns 200 and valid schema"
  - "POST /Books with invalid body returns 400"
  - "POST /Books with id and/or pagecount equal INT32 max returns 200 and valid schema"
  - "POST /Books with id and/or pagecount equal INT32 min returns 200 and valid schema"
  - "POST /Books with id and/or pagecount above INT32 max returns 400"
  - "POST /Books with id and/or pagecount below INT32 min returns 400"
  - "POST /Books without body returns 400"
  - "POST /Books with query parameters returns 200 and valid schema"
  - "POST /Books with bearer token returns 200 and valid schema"
  - "POST /Books without valid contentType headers returns 415"

- PUT /Books/{id}
  - "PUT /Books/{id} with valid request body returns 200 and valid schema"
  - "PUT /Books/{id} with empty request body returns 200 and valid schema"
  - "PUT /Books/{id} with different id in body request than the one in path returns 200,valid schema and id used in body"
  - "PUT /Books/{id} with no body id in request body returns 200, valid schema and id=0"
  - "PUT /Books/{id} with no request body returns 400"
  - "PUT /Books/{id} with beyond max int32 non valid path IDs returns 400"
  - "PUT /Books/{id} with extra body field returns 200 and valid schema"
  - "PUT /Books/{id} with query parameters returns 200 and valid schema"
  - "PUT /Books/{id} with bearer token returns 200 and valid schema"
  - "PUT /Books/{id} with quoted numeric valid IDs and pageCount values returns 200 and valid schema"
  - "PUT /Books/{id} with non integer path ID returns 400"
  - "PUT /Books/{id} with null publishDate returns 400"
  - "PUT /Books/{id} with invalid publishDate returns 400"

## Notes
- The test-case list above was assembled from @DisplayName annotations found in the test sources discovered previously. 
