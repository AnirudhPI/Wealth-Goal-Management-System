package com.systems.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import org.springframework.graphql.server.webmvc.GraphiQlHandler;

@Configuration
public class GraphiQLConfiguration {
    @Bean
    public GraphiQlHandler graphiQlHandler() {
        return new GraphiQlHandler(
                "/graphql",
                null,
                new ClassPathResource("custom-graphiql/index.html")
        );
    }

    @Bean
    public RouterFunction<ServerResponse> graphiQlRouterFunction(GraphiQlHandler graphiQlHandler) {
        return route(GET("/graphiql"), graphiQlHandler::handleRequest);
    }
}
