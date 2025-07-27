package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class SpecBooksTest extends BaseApiTest {

    public static ResponseSpecification responseSpecForBooks200;
    public static RequestSpecification requestSpecForBooks;
    public static ResponseSpecification responseSpecForBook200;

    @BeforeAll
    public static void setupSpecs(){
        requestSpecForBooks = new RequestSpecBuilder()
                .setBasePath("/api/v1/Books")
                .setContentType(ContentType.JSON)
                .build();

        responseSpecForBook200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody(matchesJsonSchemaInClasspath("schemas/book_schema.json"))
                .build();


        responseSpecForBooks200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody(matchesJsonSchemaInClasspath("schemas/books_list_schema.json"))
                .expectBody("size()", greaterThanOrEqualTo(1))
                .build();
    }
}
