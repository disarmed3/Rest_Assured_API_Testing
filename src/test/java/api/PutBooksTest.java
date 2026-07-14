package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.Matchers.*;


import java.util.Map;

public class PutBooksTest extends BooksSpecs {

    @Test
    @Tag("U1")
    @DisplayName("PUT /Books/{id} with valid request body returns 200 and valid schema")
    void givenValidRequest_whenPutBooks_thenReturn200AndValidSchema() {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(defaultBook())
                .when()
                .put("/1") //same id as defaultBook
                .then()
                .spec(responseSpecForBook200);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U2")
    @DisplayName("PUT /Books/{id} with empty request body returns 200 and valid schema")
    void givenEmptyRequest_whenPutBooks_thenReturn200AndValidSchema(int bookId) {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body("{}")
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200)
                .body("id", equalTo(0))
                .body("title", nullValue())
                .body("description", nullValue())
                .body("pageCount", equalTo(0))
                .body("excerpt", nullValue())
                .body("publishDate", equalTo("0001-01-01T00:00:00"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U3")
    @DisplayName("PUT /Books/{id} with different id in body request than the one in path returns 200,valid schema and id used in body")
    void givenDifferentBodyId_whenPutBooks_thenReturn200AndValidSchema(int bookId) {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", 100);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200)
                .body("id", equalTo(100));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U4")
    @DisplayName("PUT /Books/{id} with no body id in request body returns 200, valid schema and id=0")
    void givenNoBodyId_whenPutBooks_thenReturn200AndValidSchema(int bookId) {

        Map<String, Object> payload = defaultPayload();
        payload.remove("id");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200)
                .body("id", equalTo(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U5")
    @DisplayName("PUT /Books/{id} with no request body returns 400")
    void givenNoBodyRequest_whenPutBooks_thenReturn400(int bookId) {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .when()
                .put("/{id}", bookId)
                .then()
                .statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(longs = {-2147483649L,2147483648L})
    @Tag("U6")
    @DisplayName("PUT /Books/{id} with beyond max int32 non valid path IDs returns 400")
    void givenBeyondMaxInt32NonValidIds_whenPutBooks_thenReturn400(Long bookId) {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(defaultBook())
                .when()
                .put("/{id}", bookId)
                .then()
                .statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U7")
    @DisplayName("PUT /Books/{id} with extra body field returns 200 and valid schema")
    void givenExtraBodyField_whenPutBooks_thenReturn200AndValidSchema(int bookId) {

        Map<String, Object> payload = defaultPayload();
        payload.put("extraField", "foo-bar");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200);

    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U8")
    @DisplayName("PUT /Books/{id} with query parameters returns 200 and valid schema")
    void givenQueryParameters_whenPutBooks_thenReturn200AndValidSchema(int bookId) {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(defaultBook())
                .queryParam("foo", "42")
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200);

    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U9")
    @DisplayName("PUT /Books/{id} with bearer token returns 200 and valid schema")
    void givenBearerToken_whenPutBooks_thenReturn200AndValidSchema(int bookId) {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(defaultBook())
                .header("Authorization", "Bearer token")
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200);

    }

    @ParameterizedTest
    @ValueSource(strings= {"-2147483648", "-1", "0", "1", "200", "201", "2147483647"})
    @Tag("U10")
    @DisplayName("PUT /Books/{id} with quoted numeric valid IDs and pageCount values returns 200 and valid schema")
    void givenQuotedNumericIdsAndPageCountValues_whenPutBooks_thenReturn200andValidSchema(String bookId) {

        Map<String, Object> payload = defaultPayload();
        payload.put("id", bookId);
        payload.put("pageCount", bookId);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .put("/{id}", bookId)
                .then()
                .spec(responseSpecForBook200);
    }

    @Test
    @Tag("U11")
    @DisplayName("PUT /Books/{id} with non integer path ID returns 400")
    void givenNonIntegerPathId_whenPutBooks_thenReturn400() {

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(defaultBook())
                .when()
                .put("/abc")
                .then()
                .statusCode(400);

    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U12")
    @DisplayName("PUT /Books/{id} with null publishDate returns 400")
    void givenNullPublishDate_whenPutBooks_thenReturn400(int bookId) {

        Map<String, Object> payload = defaultPayload();
        payload.put("publishDate", null);

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .put("/{id}", bookId)
                .then()
                .statusCode(400);

    }

    @ParameterizedTest
    @ValueSource(ints = {-2147483648, -1, 0, 1, 200, 201, 2147483647})
    @Tag("U13")
    @DisplayName("PUT /Books/{id} with invalid publishDate returns 400")
    void givenInvalidPublishDate_whenPutBooks_thenReturn400(int bookId) {

        Map<String, Object> payload = defaultPayload();
        payload.put("publishDate", "22/06/2025");

        RestAssured
                .given()
                .spec(requestSpecForBooks)
                .body(payload)
                .when()
                .put("/{id}", bookId)
                .then()
                .statusCode(400);

    }

}
