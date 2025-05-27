package com.kumar.springbootlearning.mvc.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.data.filter.FilterPredicate;
import com.kumar.springbootlearning.factory.exception.BadConfigurationException;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.exception.RemoteInvocationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
public abstract class BaseRemoteClient{
    protected static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
    private static final String DELEGATE_CALL_TO = "Delegate call to {} for {}";

    protected final WebClient webClient;
    private final ComDelegateMapping mappings;
    private final ObjectMapper objectMapper;

    @Value("${logistiex.header.pod-id}")
    private String xPodId;

    @Value("${logistiex.header.tenant-id}")
    private String xTenantId;

    protected BaseRemoteClient(WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.mappings = mappings;
        this.objectMapper = objectMapper;
    }

    public <T,R> T post(String resource, R request, Class<T> responseType) throws RemoteInvocationException {
        log.info(DELEGATE_CALL_TO, getTargetService(), resource);
        try {
            return webClient.post()
                    .uri(getTargetUri(resource))
                    .headers(h -> h.addAll(getHttpHeaders()))
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (Exception ex) {
            log.error("Error while calling janus-service. ", ex);
            throw new RemoteInvocationException("Error while calling janus-service: " , ex.getMessage(), ex);
        }
    }

    public <T> Optional<T> getOne(String resource, String pathSegment, Class<T> responseType ) {
        log.info(DELEGATE_CALL_TO, getTargetService(), resource);
        String uri = getTargetUri(resource, pathSegment);
        ResponseEntity<T> clientResponse = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(responseType)
                .block();
        if (clientResponse != null) {
            return Optional.ofNullable(clientResponse.getBody());
        } else {
            throw new RemoteInvocationException("error.remote.nullResponse", "null response", uri, null);
        }
    }

    public <T> Page<T> getPage(String resource, String pathSegment, FilterPredicate filter, Pageable pageable, Class<T> responseType) {
        log.info(DELEGATE_CALL_TO, getTargetService(), resource);
        String uri = getTargetUri(resource, pathSegment);

        Mono<ResponseEntity<JsonNode>> retrievedResource = webClient.get()
                .uri(uri + prepareQueryString(filter, pageable))
                .retrieve()
                .toEntity(JsonNode.class);
        final ResponseEntity<JsonNode> clientResponse = retrievedResource.block();
        try {
            if (clientResponse != null) {
                final List<T> list = convertJsonNodeToList(clientResponse.getBody(), responseType);

                final HttpHeaders incomingHeaders = clientResponse.getHeaders();
                final List<String> totalCountHeader = incomingHeaders.get(HEADER_X_TOTAL_COUNT);
                final long totalCount = getTotalCount(list.size(), totalCountHeader);
                return PageableExecutionUtils.getPage(list, pageable, () -> totalCount);
            } else {
                throw new RemoteInvocationException("error.remote.nullResponse", "null response", uri, null);
            }
        } catch (JsonProcessingException e) {
            throw new RemoteInvocationException("error.remote.parseError", "Unable to parse response", uri, e);
        }
    }

    protected HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if(StringUtils.hasText(xPodId)) {
            headers.add("x-pod-id", xPodId);
        }
        if(StringUtils.hasText(xTenantId)) {
            headers.add("x-tenant-id", xTenantId);
        }
        return headers;
    }

    protected String getTargetUri(String resource, String pathSegment) {
        if(StringUtils.hasText(pathSegment)) {
            return getTargetUri(resource) + "/" + pathSegment;
        } else {
            return getTargetUri(resource);
        }

    }
    protected String getTargetUri(String resource) {
        final Map<String, String> map = mappings.getUri();
        if (map.containsKey(getTargetService())) {
            return map.get(getTargetService()) + "/api/"+resource;
        } else
            throw new BadConfigurationException("error.missing.delegate", "Unable to find delegate", "logistiex.delegate.service.uri." + getTargetService());
    }

    protected abstract String getTargetService();

    private String prepareQueryString(FilterPredicate filter, Pageable pageable) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (filter != null) {
            filter.getConditionsList().forEach(cond -> queryParams.addAll(com.kumar.springbootlearning.utils.StringUtils.camelToSnake(cond.field()) + ":" + cond.condition().label, cond.value()));
        }
        if (pageable != null && !pageable.isUnpaged()) {
            queryParams.add("_page", String.valueOf(pageable.getPageNumber()));
            queryParams.add("_size", String.valueOf(pageable.getPageSize()));
            pageable.getSort().forEach(order -> queryParams.add("_sort", order.getProperty() + "," + order.getDirection()));
        }
        // Build the query string using UriComponentsBuilder
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        queryParams.forEach((key, values) -> values.forEach(value -> builder.queryParam(key, value)));

        return builder.build().toUriString();
    }

    private long getTotalCount(long listSize, List<String> totalCountHeader) {
        if (totalCountHeader != null && !totalCountHeader.isEmpty()) {
            return Long.parseLong(totalCountHeader.get(0));
        }
        return listSize;
    }

    private <T> List<T> convertJsonNodeToList(JsonNode jsonNode, Class<T> objectClass) throws JsonProcessingException {
        List<T> resultList = new ArrayList<>();

        if (jsonNode != null && jsonNode.isArray()) {
            Iterator<JsonNode> elements = jsonNode.elements();

            while (elements.hasNext()) {
                JsonNode element = elements.next();
                T object = objectMapper.treeToValue(element, objectClass);
                resultList.add(object);
            }
        }
        return resultList;
    }
}