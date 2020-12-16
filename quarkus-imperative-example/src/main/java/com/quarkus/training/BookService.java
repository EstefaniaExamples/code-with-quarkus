package com.quarkus.training;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    public List<Book> getAllBooks() {
        // find all the books in the database
        return Book.listAll();
    }

    public Book getBookById(final Long id) {
        // finding a specific person by ID via an Optional
        final Optional<Book> optional = Book.findByIdOptional(id);
        return optional.orElseThrow(NotFoundException::new);
    }
}
