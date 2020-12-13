package org.springboot.training;

public class BooksHandler {
    private final BookRepository bookRepository;

    public BooksHandler(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

}
