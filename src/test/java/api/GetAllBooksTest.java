package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


public class GetAllBooksTest extends BooksSpecs{

        @Test
        @Tag("G1")
        @DisplayName("GET /Books without body returns 200 and valid schema")
        void givenNoRequestBody_whenGetBooks_thenReturn200AndValidSchema() {

            RestAssured
                    .given()
                    .spec(requestSpecForBooks)
                    .when()
                    .get()
                    .then()
                    .spec(responseSpecForBooks200);


        }

        @Test
        @Tag("G2")
        @DisplayName("GET /Books with irrelevant body returns 200 and valid schema")
        void givenIrrelevantRequestBody_whenGetBooks_thenReturn200AndValidSchema() {

            RestAssured
                    .given()
                    .spec(requestSpecForBooks)
                    .body("{\"foo\": 42}") //irrelevant body
                    .when()
                    .get()
                    .then()
                    .spec(responseSpecForBooks200);
        }

        @Test
        @Tag("G3")
        @DisplayName("GET /Books with irrelevant query parameters returns 200 and valid schema")
        void givenQueryParameters_whenGetBooks_thenReturn200AndValidSchema() {

            RestAssured
                    .given()
                    .spec(requestSpecForBooks)
                    .queryParam("foo", "42")
                    .when()
                    .get()
                    .then()
                    .spec(responseSpecForBooks200);
        }

        @Test
        @Tag("G4")
        @DisplayName("GET /Books with bearer token returns 200 and valid schema")
        void givenBearerToken_whenGetBooks_thenReturn200AndValidSchema() {

            RestAssured
                    .given()
                    .spec(requestSpecForBooks)
                    .header("Authorization", "Bearer token")
                    .when()
                    .get()
                    .then()
                    .spec(responseSpecForBooks200);
        }
}
