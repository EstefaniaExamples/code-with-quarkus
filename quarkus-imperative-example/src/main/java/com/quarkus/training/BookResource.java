package com.quarkus.training;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
public class BookResource {
    private final BookService bookService;

    public BookResource(final BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "allbooks", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public List<Book> books() {
        return bookService.getAllBooks();
    }

    @GET @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "bookbyid", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public Book bookById(@PathParam Long id) {
        return bookService.getBookById(id);
    }
}