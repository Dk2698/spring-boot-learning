package com.kumar.springbootlearning.mvc.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.data.filter.FilterPredicate;
import com.kumar.springbootlearning.data.model.BaseEntity;
import com.kumar.springbootlearning.data.model.EntityDTO;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.exception.BadConfigurationException;
import com.kumar.springbootlearning.mvc.exception.RemoteInvocationException;
import com.kumar.springbootlearning.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
public abstract class BaseRemoteService<I, D extends EntityDTO<I>, E extends BaseEntity<I>> implements RemoteService<I, D, E> {
    protected static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
    private static final String DELEGATE_CALL_TO = "Delegate call to {}";
    protected final WebClient webClient;

    private final ComDelegateMapping mappings;
    private final ObjectMapper objectMapper;

    private Class<E> entityClass;
    private Class<D> dtoClass;

    protected BaseRemoteService(WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.mappings = mappings;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page<D> findAll(FilterPredicate filter, Pageable pageable) {
        final String uri = getTargetUri() + getEntityResourceName();
        log.debug(DELEGATE_CALL_TO, uri);

        Mono<ResponseEntity<JsonNode>> retrievedResource = webClient.get()
                .uri(uri + prepareQueryString(filter, pageable))
                .retrieve()
                .toEntity(JsonNode.class);
        final ResponseEntity<JsonNode> clientResponse = retrievedResource.block();
        try {
            if (clientResponse != null) {
                final List<D> list = convertJsonNodeToList(clientResponse.getBody());

                final HttpHeaders incomingHeaders = clientResponse.getHeaders();
                final List<String> totalCountHeader = incomingHeaders.get(HEADER_X_TOTAL_COUNT);
                final long totalCount = getTotalCount(list, totalCountHeader);
                return PageableExecutionUtils.getPage(list, pageable, () -> totalCount);
            } else {
                throw new RemoteInvocationException("error.remote.nullResponse", "null response", uri, null);
            }
        } catch (JsonProcessingException e) {
            throw new RemoteInvocationException("error.remote.parseError", "Unable to parse response", uri, e);
        }
    }

    @Override
    public Page<D> findAll(Pageable pageable) {
        return findAll(null, pageable);
    }

    @Override
    public Optional<D> findOne(I id) {
        final String uri = getTargetUri() + getEntityResourceName() + "/" + id;
        log.debug(DELEGATE_CALL_TO, uri);
        Mono<ResponseEntity<D>> retrievedResource = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(getDTOClass());
        final ResponseEntity<D> clientResponse = retrievedResource.block();
        if (clientResponse != null) {
            return Optional.ofNullable(clientResponse.getBody());
        } else {
            throw new RemoteInvocationException("error.remote.nullResponse", "null response", uri, null);
        }
    }

    public String getTargetUri() {

        final Map<String, String> map = mappings.getUri();
        if (map.containsKey(getTargetService())) {
            return map.get(getTargetService()) + "/api/";
        } else {
            throw new BadConfigurationException("error.missing.delegate", "Unable to find delegate", "logistiex.delegate.service.uri." + getTargetService());
        }
    }

    @Override
    public Class<E> getEntityClass() {
        if (entityClass == null) {
            ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
            entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[2];
        }
        return entityClass;
    }

    private Class<D> getDTOClass() {
        if (dtoClass == null) {
            ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
            dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        }
        return dtoClass;
    }

    private List<D> convertJsonNodeToList(JsonNode jsonNode) throws JsonProcessingException {
        List<D> resultList = new ArrayList<>();

        if (jsonNode != null && jsonNode.isArray()) {
            Iterator<JsonNode> elements = jsonNode.elements();

            while (elements.hasNext()) {
                JsonNode element = elements.next();
                D object = objectMapper.treeToValue(element, getDTOClass());
                resultList.add(object);
            }
        }
        return resultList;
    }

//    private String convertPageableToQueryString(Pageable pageable) {
//        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
//
//        // Add page number, size, and sorting information to the query parameters
//        queryParams.add("_page", String.valueOf(pageable.getPageNumber()));
//        queryParams.add("_size", String.valueOf(pageable.getPageSize()));
//        pageable.getSort().forEach(order -> queryParams.add("_sort", order.getProperty() + "," + order.getDirection()));
//
//        // Build the query string using UriComponentsBuilder
//        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
//        queryParams.forEach((key, values) -> values.forEach(value -> builder.queryParam(key, value)));
//
//        return builder.build().toUriString();
//    }
//
//    private String convertFilterToQueryString(FilterPredicate filter) {
//        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
//
//        filter.getConditionsList().forEach(cond -> queryParams.addAll(StringUtils.camelToSnake(cond.field()) + ":" + cond.condition().label, cond.value()));
//
//        // Build the query string using UriComponentsBuilder
//        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
//        queryParams.forEach((key, values) -> values.forEach(value -> builder.queryParam(key, value)));
//
//        return builder.build().toUriString();
//    }

    private String prepareQueryString(FilterPredicate filter, Pageable pageable) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (filter != null) {
            filter.getConditionsList().forEach(cond -> queryParams.addAll(StringUtils.camelToSnake(cond.field()) + ":" + cond.condition().label, cond.value()));
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

    private long getTotalCount(List<D> list, List<String> totalCountHeader) {
        long totalCount = list.size();
        if (totalCountHeader != null && !totalCountHeader.isEmpty()) {
            totalCount = Long.parseLong(totalCountHeader.get(0));
        }
        return totalCount;
    }

    protected abstract String getEntityResourceName();
}
