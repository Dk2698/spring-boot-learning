package com.kumar.springbootlearning.async.config;

import com.kumar.springbootlearning.pattern.singleton.context.TenantContext;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@Configuration
@EnableAsync()
@ConditionalOnProperty(value = "com.async.enabled", havingValue = "true")
public class AsyncConfiguration implements AsyncConfigurer {

    public static final String PRIMARY_EXECUTOR_NAME = "primary";
    private final AsyncProperties asyncProperties;
    private final TenantContext tenantContext;

    public AsyncConfiguration(AsyncProperties asyncProperties, TenantContext tenantContext) {
        this.asyncProperties = asyncProperties;
        this.tenantContext = tenantContext;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new SimpleAsyncUncaughtExceptionHandler();
        return ((ex, method, params) -> {
            System.err.println("Exception in async method: "+ ex.getMessage());
        });
    }

    public ContextPropagationDecorator getContextPropagationDecorator() {
        return new ContextPropagationDecorator(tenantContext);
    }

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        final ExecutorConfig primaryExecutor = asyncProperties.getExecutorConfig(PRIMARY_EXECUTOR_NAME);
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(primaryExecutor.getCorePoolSize());
        taskExecutor.setMaxPoolSize(primaryExecutor.getMaxPoolSize());
        taskExecutor.setQueueCapacity(primaryExecutor.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(primaryExecutor.getKeepAliveSeconds());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setThreadNamePrefix("primary-async-");
        taskExecutor.setTaskDecorator(getContextPropagationDecorator());
        return taskExecutor;
    }

    @Bean(name = "taskExecutorForHeavyTasks")
    public ThreadPoolTaskExecutor taskExecutorRegistration() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async2-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "secondaryExecutor")
    public Executor secondaryExecutor() {
        ExecutorConfig config = asyncProperties.getExecutorConfig("secondary");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setThreadNamePrefix("Async-secondary-");
        executor.initialize();
        return executor;
    }
}