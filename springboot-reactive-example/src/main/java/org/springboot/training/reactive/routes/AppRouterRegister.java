package org.springboot.training.reactive.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AppRouterRegister {
    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/test"),
                req -> ok().body(Mono.just("Hello World!"), String.class));
    }
}
