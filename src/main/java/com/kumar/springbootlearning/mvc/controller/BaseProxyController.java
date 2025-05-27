package com.kumar.springbootlearning.mvc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.exception.BadConfigurationException;
import com.kumar.springbootlearning.valuelist.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BaseProxyController<I> extends BaseController {

    protected final WebClient webClient;

    private final ComDelegateMapping mappings;

    protected BaseProxyController(WebClient webClient, ComDelegateMapping mappings) {
        this.webClient = webClient;
        this.mappings = mappings;
    }

    @GetMapping("/{entity}")
    public ResponseEntity<JsonNode> getAllEntities(@PathVariable String entity, HttpServletRequest request) {
        log.debug("REST request to get a page of {}", entity);
        final String queryString = request.getQueryString() == null ? "" : URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        final String uri = getTargetUri() + entity + "?" + queryString;
        log.debug("Delegate call to {}", uri);
        Mono<ResponseEntity<JsonNode>> retrievedResource = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);

        final ResponseEntity<JsonNode> clientResponse = retrievedResource.block();
        if (clientResponse != null) {
            final HttpHeaders incomingHeaders = clientResponse.getHeaders();
            HttpHeaders headers = new HttpHeaders();
            final List<String> totalCountHeader = incomingHeaders.get(HEADER_X_TOTAL_COUNT);
            if (totalCountHeader != null && !totalCountHeader.isEmpty()) {
                headers.add(HEADER_X_TOTAL_COUNT, totalCountHeader.get(0));
            }
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(clientResponse.getBody());
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @PostMapping(value = "/{entity}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> createEntity(@PathVariable String entity, @RequestBody JsonNode dto) {
        log.debug("REST request to save Entity : {}", entity);
        final String uri = getTargetUri() + entity;
        log.debug("Delegate call to {}", uri);
        Mono<ResponseEntity<JsonNode>> retrievedResource = webClient.post()
                .uri(uri)
                .bodyValue(dto)
                .retrieve()
                .toEntity(JsonNode.class);

        final ResponseEntity<JsonNode> clientResponse = retrievedResource.block();
        return ResponseEntity.created(URI.create("/"))
                .body(clientResponse.getBody());
    }

    @GetMapping("/{entity}/{id}")
    public ResponseEntity<JsonNode> getAllEntities(@PathVariable String entity, @PathVariable I id, HttpServletRequest request) {
        final String queryString = request.getQueryString() == null ? "" : URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        final String uri = getTargetUri() + entity + "/" + id + "?" + queryString;
        log.debug("Delegate call to {}", uri);
        Mono<ResponseEntity<JsonNode>> retrievedResource = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(JsonNode.class);

        final ResponseEntity<JsonNode> clientResponse = retrievedResource.block();
        return ResponseEntity.ok()
                .body(clientResponse.getBody());
    }

    public String getTargetUri() {

        final Map<String, String> map = mappings.getUri();
        if (map.containsKey(getTargetService())) {
            return map.get(getTargetService()) + "/api/";
        } else {
            throw new BadConfigurationException("error.missing.delegate", "Unable to find delegate", "logistiex.delegate.service.uri." + getTargetService());
        }
    }

    public abstract String getTargetService();
}