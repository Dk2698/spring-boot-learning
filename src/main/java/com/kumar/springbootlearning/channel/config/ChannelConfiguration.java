package com.kumar.springbootlearning.channel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class ChannelConfiguration {

    @Bean
//    WebClient channelWebClient(OAuth2AuthorizedClientManager authorizedClientManager) {
    WebClient channelWebClient() {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//        oauth.setDefaultClientRegistrationId("lgtx");
        return WebClient.builder()
//                .filter(oauth)
                .filter(errorHandler())
                .build();
    }

    public ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                log.error("5XX Error while calling delegate - {}", clientResponse.statusCode());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)));
            } else if (clientResponse.statusCode().is4xxClientError()) {
                log.error("4XX Error while calling delegate - {}", clientResponse.statusCode());
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }
}
