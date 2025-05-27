package com.kumar.springbootlearning.strategy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient publicWebClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create().followRedirect((req, res) -> req.redirectedFrom().length < 3);
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        return builder
                .clientConnector(connector)
                .build();
    }
}
