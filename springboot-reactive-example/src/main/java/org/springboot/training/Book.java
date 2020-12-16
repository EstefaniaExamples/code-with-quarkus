package org.springboot.training;


import org.springframework.data.annotation.Id;

public class Book {
    @Id
    public Long id;
    public String title;
    public String description;
    public String author;

    public Book() {
    }

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Book(Long id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }
}
