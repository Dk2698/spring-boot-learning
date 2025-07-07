package com.kumar.springbootlearning.cache;

import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final WebClient webClient;
    private final ComDelegateMapping mappings;

    public ProductService(WebClient webClient, ComDelegateMapping mappings) {
        this.webClient = webClient;
        this.mappings = mappings;
    }

    public String getTargetUri() {
        Map<String, String> map = mappings.getUri();
        if (map.containsKey("product-core")) {
            return map.get("product-core") + "/api/products/";
        } else {
            throw new IllegalStateException("Missing delegate configuration for product-core");
        }
    }

    @Cacheable(value = "products", key = "#productId", unless = "#result == null")
    public Optional<ProductDTO> getProductById(String productId, String searchkey) {
        log.info("Cache miss for productId: {}", productId);

        String uri = getTargetUri() + productId;

        try {
            Mono<ResponseEntity<ProductDTO>> response = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .toEntity(ProductDTO.class);

            ResponseEntity<ProductDTO> result = response.block();

            if (result != null && result.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(result.getBody());
            } else {
                log.warn("Empty or failed response for productId: {}", productId);
            }
        } catch (Exception ex) {
            log.error("Error calling product-core: {}", ex.getMessage());
            // Fallback mock data for demo
            return Optional.of(new ProductDTO(productId, "Demo Product", "Sample product for ID: " + productId, "demo"));
        }

        return Optional.empty();
    }


//    @Cacheable("products")
//    public Product getProductById(String id) {
//        System.out.println("Fetching from DB/API for ID: " + id);
//        return new Product("1", "Test Product");
//    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(String id) {
        System.out.println("Deleted product and cleared cache for ID: " + id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void clearAllCache() {
        System.out.println("Cleared entire cache");
    }

    @CachePut(value = "products", key = "#product.id")
    public ProductDTO updateProduct(ProductDTO product) {
        // Save to DB
        return product;
    }

//    @Cacheable(value = "products", key = "#id", condition = "#id.startsWith('A')")
//    public Product getProductById(String id) {
//        // Will only cache if ID starts with 'A'
//        return productRepository.findById(id);
//    }

    public List<ProductDTO> getAllProducts() {
        return Arrays.asList(
                new ProductDTO("123", "Demo Product", "Sample product for ID: 123", "demo"),
                new ProductDTO("124", "Test Product", "Test product for ID: 124", "test"),
                new ProductDTO("125", "Another Product", "Another product for ID: 125", "general")
        );
    }

//    @Cacheable(value = "products", key = "#productId", unless = "#result == null")
//    public ProductDTO getProductById(String productId) {
//        log.info("[Cache Miss] Fetching product by ID: {}", productId);
//        return getAllProducts()
//                .stream()
//                .filter(product -> product.getId().equals(productId))
//                .findFirst()
//                .orElse(null);
//    }

    @Cacheable(value = "products", keyGenerator = "customKeyGenerator", unless = "#result == null")
    public ProductDTO getProductById(String productId) {
        log.info("[Cache Miss] Fetching product by ID: {}", productId);
        return getAllProducts()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

}
