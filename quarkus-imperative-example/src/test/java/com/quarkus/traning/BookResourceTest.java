package com.quarkus.traning;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BookResourceTest {
    @Test
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() {
        given()
                .when().get("/books")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":997,\"title\":\"Understanding Bean Validation\",\"description\":\"In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints\",\"author\":\"Antonio Goncalves\"}," +
                        "{\"id\":998,\"title\":\"Understanding JPA\",\"description\":\"In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle\",\"author\":\"Antonio Goncalves\"}]"));
    }

    @Test
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() {
        given()
                .pathParam("id", 997)
                .when().get("/books/{id}")
                .then()
                .statusCode(200)
                .body(is("{\"id\":997,\"title\":\"Understanding Bean Validation\",\"description\":\"In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints\",\"author\":\"Antonio Goncalves\"}"));
    }

    @Test
    @DisplayName("The service should response not found when the bood id does not exist")
    public void shouldNotGetBookById() {
        given()
                .pathParam("id", 91)
                .when().get("/books/{id}")
                .then()
                .statusCode(404);
    }
}
