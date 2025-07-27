package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


public class PostBooksTest extends SpecBooksTest{
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Book defaultBook() {
        return new Book(1, "Book 1", "Foo bar", 100, "Foo bar", "2026-06-28");
    }

    private static Map<String, Object> defaultPayload() {
        return MAPPER.convertValue(defaultBook(), Map.class);
    }

    @Test
    @Tag("P1")
    @DisplayName("POST /Books with empty request body returns 200 and valid schema")
    void givenEmptyRequest_whenPostBooks_thenReturn200AndValidSchema() {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body("{}")
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);
    }

    @Test
    @Tag("P2")
    @DisplayName("POST /Books with valid request body returns 200 and valid schema")
    void givenValidRequest_whenPostBooks_thenReturn200AndValidSchema() {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(defaultBook())
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P3")
    @DisplayName("POST /Books with id as string returns 200 and valid schema")
    void givenIdAsString_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", "0");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P4")
    @DisplayName("POST /Books with pagecount as string returns 200 and valid schema")
    void givenPagecountAsString_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.put("pageCount", "100");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P5")
    @DisplayName("POST /Books with missing one or more key value pairs returns 200 and valid schema")
    void givenMissingOneOrMoreKeyValuePairs_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.remove("pageCount");
        payload.remove("excerpt");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);


    }

    @Test
    @Tag("P6")
    @DisplayName("POST /Books with null publishDate returns 400")
    void givenNullPublishDate_whenPostBooks_thenReturn400() {

        Map<String, Object> payload = defaultPayload();
        payload.put("publishDate", null);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P7")
    @DisplayName("POST /Books with invalid date format for publishDate returns 400")
    void givenInvalidDateFormat_whenPostBooks_thenReturn400() {

        Map<String, Object> payload = defaultPayload();
        payload.put("publishDate", "22/06/2025");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P8")
    @DisplayName("POST /Books with text fields set to null returns 200 and valid schema")
    void givenNullTextFields_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.put("title", null);
        payload.put("excerpt", null);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P9")
    @DisplayName("POST /Books with id and/or pagecount set to null returns 400")
    void givenNullIdAndOrPagecount_whenPostBooks_thenReturn400() {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", null);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P10")
    @DisplayName("POST /Books with extra body field returns 200 and valid schema")
    void givenExtraBodyField_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.put("extraField", "foo-bar");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P11")
    @DisplayName("POST /Books with invalid body returns 400")
    void givenInvalidBody_whenPostBooks_thenReturn400() {

        String payload = "{\"just a string\"}";

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P12")
    @DisplayName("POST /Books with id and/or pagecount equal INT32 max returns 200 and valid schema")
    void givenIdAndOrPagecountEqualToINT32Max_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", 2147483647);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P13")
    @DisplayName("POST /Books with id and/or pagecount equal INT32 min returns 200 and valid schema")
    void givenIdAndOrPagecountEqualToINT32Min_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", -2147483648);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P14")
    @DisplayName("POST /Books with id and/or pagecount above INT32 max returns 400")
    void givenIdAndOrPagecountAboveINT32Max_whenPostBooks_thenReturn400() {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", 2147483648L);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P15")
    @DisplayName("POST /Books with id and/or pagecount below INT32 min returns 400")
    void givenIdAndOrPagecountBelowINT32Min_whenPostBooks_thenReturn400() {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", -2147483649L);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P16")
    @DisplayName("POST /Books without body returns 400")
    void givenWithoutBody_whenPostBooks_thenReturn400() {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    @Tag("P17")
    @DisplayName("POST /Books with query parameters returns 200 and valid schema")
    void givenQueryParameters_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .queryParam("foo", "42")
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P18")
    @DisplayName("POST /Books with bearer token returns 200 and valid schema")
    void givenBearerToken_whenPostBooks_thenReturn200AndValidSchema() {

        Map<String, Object> payload = defaultPayload();

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .header("Authorization", "Bearer token")
                .when()
                .post()
                .then()
                .spec(responseSpecForBook200);

    }

    @Test
    @Tag("P19")
    @DisplayName("POST /Books without valid contentType headers returns 415")
    void givenWithoutContentType_whenPostBooks_thenReturn415() {

        Map<String, Object> payload = defaultPayload();

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .contentType(ContentType.XML)
                .body(payload)
                .when()
                .post()
                .then()
                .statusCode(415);

    }


}
