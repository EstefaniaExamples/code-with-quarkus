package org.springboot.training;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonProperty
    public Long id;
    @JsonProperty
    public String title;
    @JsonProperty
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
