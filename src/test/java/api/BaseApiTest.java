package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;

public abstract class BaseApiTest {

    @BeforeAll
    static void configureRestAssured() {
        RestAssured.baseURI  = "https://fakerestapi.azurewebsites.net/";
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));
    }
}

