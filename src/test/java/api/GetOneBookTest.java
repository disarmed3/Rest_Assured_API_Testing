package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GetOneBookTest extends BooksSpecs{

        @ParameterizedTest
        @ValueSource(ints = {1, 100, 200})
        @Tag("G3.1")
        @DisplayName("GET /Books/{id} with edge (1,200) and middle (100) valid IDs returns 200 and valid schema")
        void givenEdgeAndMiddleValidIds_whenGetOneBook_thenReturn200AndValidSchema(int bookId) {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .when()
                        .get("/{id}", bookId)
                        .then()
                        .spec(responseSpecForBook200);

        }

        @ParameterizedTest
        @ValueSource(ints = {0, 201})
        @Tag("G3.2")
        @DisplayName("GET /Books/{id} with edge (0,201) non valid IDs returns 404")
        void givenEdgeNonValidIds_whenGetOneBook_thenReturn404(int bookId) {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .when()
                        .get("/{id}", bookId)
                        .then()
                        .statusCode(404);

        }

        @ParameterizedTest
        @ValueSource(ints = {-2147483648, 2147483647})
        @Tag("G3.3")
        @DisplayName("GET /Books/{id} with equal to max int32 non valid IDs returns 404")
        void givenMaxInt32NonValidIds_whenGetOneBook_thenReturn404(int bookId) {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .when()
                        .get("/{id}", bookId)
                        .then()
                        .statusCode(404);

        }

        @ParameterizedTest
        @ValueSource(longs = {-2147483649L, 2147483648L})
        @Tag("G3.4")
        @DisplayName("GET /Books/{id} with beyond max int32 non valid IDs returns 400")
        void givenBeyondMaxInt32NonValidIds_whenGetOneBook_thenReturn400(long bookId) {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .when()
                        .get("/{id}", bookId)
                        .then()
                        .statusCode(400);

        }

        @Test
        @Tag("G3.5")
        @DisplayName("GET /Books/{id} with non integer path ID returns 400")
        void givenNonIntegerPathId_whenGetOneBook_thenReturn400() {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .when()
                        .get("/abc")
                        .then()
                        .statusCode(400);

        }

        @ParameterizedTest
        @ValueSource(strings = {"01", " 1", "1 "})
        @Tag("G3.6")
        @DisplayName("GET /Books/{id} with quoted numeric valid IDs returns 200 and valid schema")
        void givenQuotedNumericIds_whenGetOneBook_thenReturn200andValidSchema(String bookId) {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .when()
                        .get("/{id}", bookId)
                        .then()
                        .spec(responseSpecForBook200);

        }

        @Test
        @Tag("G3.7")
        @DisplayName("GET /Books/{id} with irrelevant body returns 200 and valid schema")
        void givenIrrelevantBody_whenGetOneBook_thenReturn200andValidSchema() {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .body("{\"foo\": 42}")
                        .when()
                        .get("/1")
                        .then()
                        .spec(responseSpecForBook200);

        }

        @Test
        @Tag("G3.8")
        @DisplayName("GET /Books/{id} with query parameters returns 200 and valid schema")
        void givenQueryParameters_whenGetOneBook_thenReturn200andValidSchema() {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .queryParam("foo", "42")
                        .when()
                        .get("/1")
                        .then()
                        .spec(responseSpecForBook200);

        }

        @Test
        @Tag("G3.9")
        @DisplayName("GET /Books/{id} with bearer token returns 200 and valid schema")
        void givenBearerToken_whenGetOneBook_thenReturn200andValidSchema() {

                RestAssured
                        .given()
                        .spec(requestSpecForBooks)
                        .header("Authorization", "Bearer token")
                        .when()
                        .get("/1")
                        .then()
                        .spec(responseSpecForBook200);
        }
}
