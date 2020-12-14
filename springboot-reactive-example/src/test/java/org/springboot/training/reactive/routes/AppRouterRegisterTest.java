package org.springboot.training.reactive.routes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppRouterRegisterTest {
    private static final String DATASOURCE_URL_PROPERTY = "spring.r2dbc.url";
    private static final String DATASOURCE_URL_USER = "spring.r2dbc.username";
    private static final String DATASOURCE_URL_PASSWORD = "spring.r2dbc.password";

    private final WebTestClient webTestClient;

    public AppRouterRegisterTest(@Autowired WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:11"))
            .withDatabaseName("books_database")
            .withUsername("book")
            .withPassword("book")
            .withExposedPorts(5432)
            .withInitScript("import-test.sql");

    @DynamicPropertySource
    static void setUpProperties(final DynamicPropertyRegistry registry) {
        final String pgR2dbcUrl = POSTGRE_SQL_CONTAINER.getJdbcUrl().replaceAll("jdbc", "r2dbc");
        registry.add(DATASOURCE_URL_PROPERTY, () -> pgR2dbcUrl);
        registry.add(DATASOURCE_URL_USER, POSTGRE_SQL_CONTAINER::getUsername);
        registry.add(DATASOURCE_URL_PASSWORD, POSTGRE_SQL_CONTAINER::getPassword);
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() throws Exception {
        webTestClient.get().uri("/books")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.[0].id").isEqualTo(997)
                .jsonPath("$.[0].title").isEqualTo("Understanding Bean Validation")
                .jsonPath("$.[0].isbn_13").isEqualTo("9781980399025")
                .jsonPath("$.[1].id").isEqualTo(998)
                .jsonPath("$.[1].title").isEqualTo("Understanding JPA")
                .jsonPath("$.[1].isbn_13").isEqualTo("9781093918977");
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() throws Exception {
        webTestClient.get().uri("/books/{id}", 998)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(998)
                .jsonPath("$.title").isEqualTo("Understanding JPA")
                .jsonPath("$.isbn_13").isEqualTo("9781093918977");
    }

    @Test
    @Order(0)
    @DisplayName("The service should response ok when deleting a not existing book")
    public void shouldDeleteNotExistingBookById() throws Exception {
        webTestClient.delete().uri("/books/{id}", 91)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();
    }

    @Test
    @Order(1)
    @DisplayName("The service should response ok when deleting an existing book")
    public void shouldDeleteBookById() throws Exception {
        webTestClient.delete().uri("/books/{id}", 998)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }
}
