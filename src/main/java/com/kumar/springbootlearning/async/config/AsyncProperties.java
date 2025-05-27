package com.kumar.springbootlearning.async.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "com.async")
public class AsyncProperties {
    private static final ExecutorConfig DEFAULT_EXECUTOR_CONFIG = new ExecutorConfig();
    private Map<String, ExecutorConfig> executors = new HashMap<>();
    private boolean enabled;

    public ExecutorConfig getExecutorConfig(String executorName) {
        return executors.getOrDefault(executorName, DEFAULT_EXECUTOR_CONFIG);
    }
}
