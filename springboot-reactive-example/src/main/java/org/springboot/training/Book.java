package org.springboot.training;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class Book {
    @Id
    public Long id;
    public String title;
    @JsonProperty("isbn_13")
    public String isbn_13;

    public Book() {
    }

    public Book(String title, String isbn_13) {
        this.title = title;
        this.isbn_13 = isbn_13;
    }

    public Book(Long id, String title, String isbn_13) {
        this.id = id;
        this.title = title;
        this.isbn_13 = isbn_13;
    }
}
