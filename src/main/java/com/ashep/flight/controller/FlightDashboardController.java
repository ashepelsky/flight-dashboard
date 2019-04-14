package com.ashep.flight.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
// webflux doesn't support plain spring controller to serve static content
public class FlightDashboardController {

    @Bean
    public RouterFunction<ServerResponse> htmlRouter(
            @Value("classpath:/static/dashboard.html")
                    Resource html) {

        return route(GET("/dashboard"), request
                -> ok().contentType(MediaType.TEXT_HTML).syncBody(html)
        );
    }
}
