package com.kumar.springbootlearning.client.shipping.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class ShippingConfiguration {

    /**
     * spring.security.oauth2.client.provider.shipping.issuer-uri=${SHIPPING_ISSUER_SERVER:https://com.kumar.com/realms/shipping-api-dev}
     * spring.security.oauth2.client.registration.shipping.client-id=${APP_NAME:@project.name@}
     * spring.security.oauth2.client.registration.shipping.client-secret=${SHIPPING_CLIENT_SECRET:}
     * spring.security.oauth2.client.registration.shipping.authorization-grant-type=client_credentials
     * spring.security.oauth2.client.registration.shipping.scope=openid,profile,email,offline_access,roles
     */

    @Bean
    WebClient shippingWebClient() {
//    WebClient shippingWebClient(OAuth2AuthorizedClientManager authorizedClientManager) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//        oauth.setDefaultClientRegistrationId("shipping");
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
