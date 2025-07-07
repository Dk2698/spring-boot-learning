package com.kumar.springbootlearning.cache;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheWarmUp {

    private final ProductService productService;

    public CacheWarmUp(ProductService productService) {
        this.productService = productService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {
        List<String> preloadProductIds = List.of("123", "456");

        preloadProductIds.forEach(id -> {
            ProductDTO dto = productService.getProductById(id);  // cache will auto-fill
            if (dto != null) {
                System.out.println("Warmed cache for product ID: " + id);
            }
        });
    }
}

