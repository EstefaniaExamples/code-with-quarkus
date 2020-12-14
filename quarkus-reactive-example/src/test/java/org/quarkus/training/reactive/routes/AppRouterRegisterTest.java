package org.quarkus.training.reactive.routes;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppRouterRegisterTest  {
    @Test
    @Order(0)
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() {
        given()
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, is(MediaType.APPLICATION_JSON))
                .body(is("[ {\n" +
                        "  \"id\" : 997,\n" +
                        "  \"title\" : \"Understanding Bean Validation\",\n" +
                        "  \"isbn_13\" : \"9781980399025\"\n" +
                        "}, {\n" +
                        "  \"id\" : 998,\n" +
                        "  \"title\" : \"Understanding JPA\",\n" +
                        "  \"isbn_13\" : \"9781093918977\"\n" +
                        "} ]"));
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() {
        given()
                .when()
                .get("/books/998")
                .then()
                .statusCode(200)
                .body(is("{\n" +
                        "  \"id\" : 998,\n" +
                        "  \"title\" : \"Understanding JPA\",\n" +
                        "  \"isbn_13\" : \"9781093918977\"\n" +
                        "}"));
    }

    @Test
    @Order(0)
    @DisplayName("The service should response not found when deleting a not existing book")
    public void shouldDeleteNotExistingBookById() {
        given()
                .when()
                .delete("/books/91")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(1)
    @DisplayName("The service should response ok when deleting an existing book")
    public void shouldDeleteBookById() {
        given()
                .when()
                .delete("/books/998")
                .then()
                .statusCode(200);
    }
}
