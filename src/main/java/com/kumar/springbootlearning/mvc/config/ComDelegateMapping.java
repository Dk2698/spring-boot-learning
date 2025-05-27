package com.kumar.springbootlearning.mvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "com.delegate.service")
@Data
public class ComDelegateMapping {
    private Map<String, String> uri;
}
