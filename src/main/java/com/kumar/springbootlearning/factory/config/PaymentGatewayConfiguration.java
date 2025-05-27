package com.kumar.springbootlearning.factory.config;

import com.kumar.springbootlearning.factory.exception.BadConfigurationException;
import com.kumar.springbootlearning.factory.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class PaymentGatewayConfiguration {

    @Bean
    WebClient payuWebClient() {
        return WebClient.builder()
                .defaultHeaders(this::addDefaultHeaders)
                .filter(errorHandler())
                .build();
    }

    public ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                log.error("5XX Error while calling delegate - {}", clientResponse.statusCode());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)));
            } else if (clientResponse.statusCode().value() == 404) {
                log.error("404 Error while calling delegate - {}", clientResponse.statusCode());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, errorBody)));
            } else if (clientResponse.statusCode().value() == 401 || clientResponse.statusCode().value() == 403) {
                log.error("Auth Error while calling delegate - {}", clientResponse.statusCode());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new BadConfigurationException("error.invalid.credentials", "Invalid Client Credentials", "spring.security.oauth2.client.registration.lgtx")));
            } else if (clientResponse.statusCode().is4xxClientError()) {
                log.error("4XX Error while calling delegate - {}", clientResponse.statusCode());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new BadRequestException("Invalid Input", errorBody, "error.invalid.input")));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }

    private void addDefaultHeaders(final HttpHeaders headers) {
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
    }
}