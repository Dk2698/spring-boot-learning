package com.kumar.springbootlearning.async.config;

import lombok.Data;

@Data
public class ExecutorConfig {
    private int corePoolSize = 5;
    private int maxPoolSize = 10;
    private int queueCapacity = 500;
    private int keepAliveSeconds = 60;
}
