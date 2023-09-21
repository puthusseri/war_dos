package com.tyson.useless.system;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig {

    @Bean
    public ErrorPage error429Page() {
        return new ErrorPage(HttpStatus.TOO_MANY_REQUESTS, "/error-429");
    }
}
