package org.quarkus.training.routing.layer;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AppRouterRegisterTest  {
    @Test
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
    public void shouldGetASpecificBook() {
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
}
