package org.springboot.training;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    private final BookRepository bookRepository;

    public AppController(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
