package com.kumar.springbootlearning.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.controller.BaseProxyController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/storage-service")
@Slf4j
public class StorageServiceController extends BaseProxyController<String> {

    public StorageServiceController(WebClient webClient, ComDelegateMapping mappings) {
        super(webClient,mappings);
    }

    @PostMapping(value = "/{entity}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonNode> createEntity(@PathVariable String entity, @RequestPart("fileContent") MultipartFile multipartFile, @RequestParam Map<String, String> params ) {
        log.debug("REST request to save Entity : {}", entity);
        final String uri = getTargetUri() + entity;
        log.debug("Delegate call to {}", uri);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("fileContent", multipartFile.getResource());
        params.keySet().forEach(key -> builder.part(key, params.get(key)));
        Mono<ResponseEntity<JsonNode>> retrievedResource = webClient.post()
                .uri(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .toEntity(JsonNode.class);

        final ResponseEntity<JsonNode> clientResponse = retrievedResource.block();
        return ResponseEntity.created(URI.create("/"))
                .body(clientResponse.getBody());
    }

    @Override
    public String getTargetService() {
        return "storage-service";
    }
}