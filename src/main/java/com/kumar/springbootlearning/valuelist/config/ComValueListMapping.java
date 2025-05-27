package com.kumar.springbootlearning.valuelist.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "com.value-lists")
@Data
public class ComValueListMapping {
    private Map<String, String> mappings;
}
