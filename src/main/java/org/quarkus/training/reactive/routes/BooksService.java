package org.quarkus.training.reactive.routes;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.quarkus.training.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

@ApplicationScoped
public class BooksService {
    private static final Logger LOG = LoggerFactory.getLogger(BooksService.class);

    private final PgPool pgPool;

    @Inject
    public BooksService(final PgPool pgPool) {
        this.pgPool = pgPool;
    }

    public Multi<Book> findAllBooks() {
        LOG.info("Get book by id ...");
        return Book.findAll(pgPool);
    }

    public Uni<Response> findBookById(final Long id) {
        LOG.info("Get book by id ...");
        return Book.findById(pgPool, id)
                .onItem().transform(book -> book != null ? Response.ok(book) : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    public Uni<Response> saveBook(final Book book) {
        LOG.info("Get book by id ...");
        return book.save(pgPool)
                .onItem().transform(id -> URI.create("/books/" + id))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    public Uni<Response> deleteBook(final Long id) {
        LOG.info("Get book by id ...");
        return Book.delete(pgPool, id)
                .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }
}
