package com.kumar.springbootlearning.mvc.config;

import com.kumar.springbootlearning.factory.exception.BadRequestException;
import com.kumar.springbootlearning.mvc.exception.BadConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${com.header.pod-id:#{null}}")
    private String xPodId;

    @Value("${com.header.tenant-id:#{null}}")
    private String xTenantId;

//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientService authorizedClientService) {
//
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .clientCredentials()
//                        .build();
//
//        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientService);
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return authorizedClientManager;
//    }

//    @Bean
//    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//        oauth.setDefaultClientRegistrationId("lgtx");
//        return WebClient.builder()
//                .exchangeStrategies(ExchangeStrategies
//                        .builder()
//                        .codecs(codecs -> codecs
//                                .defaultCodecs()
//                                .maxInMemorySize(5000 * 1024))
//                        .build())
//                .filter(oauth)
//                .filter(errorHandler())
//                .defaultHeaders(this::addDefaultHeaders)
//                .build();
//    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(5000 * 1024))
                        .build())
//                .filter(oauth)
                .filter(errorHandler())
                .defaultHeaders(this::addDefaultHeaders)
                .build();
    }

//    @Bean
//    public WebClient publicWebClient(WebClient.Builder builder) {
//        HttpClient httpClient = HttpClient.create().followRedirect((req, res) -> req.redirectedFrom().length < 3);
//        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
//        return builder
//                .clientConnector(connector)
//                .build();
//    }

    private void addDefaultHeaders(final HttpHeaders headers) {
        if (StringUtils.hasText(xPodId)) {
            headers.add("x-pod-id", xPodId.trim());
        }
        if (StringUtils.hasText(xTenantId)) {
            headers.add("x-tenant-id", xTenantId.trim());
        }
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
}
