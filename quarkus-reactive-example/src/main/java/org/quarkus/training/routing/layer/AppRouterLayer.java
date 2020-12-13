package org.quarkus.training.routing.layer;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.reactivex.annotations.NonNull;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.quarkus.training.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.vertx.core.http.HttpMethod.*;

@RouteBase(path = "/declarative", produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
public class AppRouterLayer {
    private static final Logger LOG = LoggerFactory.getLogger(AppRouterLayer.class);

    private final BooksService booksService;

    @Inject
    public AppRouterLayer(final BooksService booksService) {
        this.booksService = booksService;
    }

    @Route(methods = GET, path = "/books", produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON)
    public Multi<Book> getAllBooks() {
        return booksService.findAllBooks();
    }

    @Route(methods = GET, path = "/books/:id", produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON)
    public Uni<Response> getSingle(@Param String id) {
        return booksService.findBookById(Long.valueOf(id));
    }

    @Route(methods = POST, path = "/book", produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON)
    public Uni<Response> create(@Valid @NonNull @Body Book book) {
        return booksService.saveBook(book);
    }

    @Route(methods = DELETE, path = "/books/:id", produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON)
    public Uni<Response> delete(@Param String id) {
        return booksService.deleteBook(Long.valueOf(id));
    }
}
