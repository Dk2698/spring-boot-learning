package com.kumar.springbootlearning.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "com.cache")
public class ComCacheProperties {
    private Map<String, CacheManagerProperties> caches;
}