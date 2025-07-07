//package com.kumar.springbootlearning.cache;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.concurrent.NoOpCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//@Configuration
//public class DevToolsAwareCacheConfig {
//
//    @Bean
//    @ConditionalOnMissingBean(CacheManager.class)
//    public CacheManager devtoolsSafeCacheManager(Environment env) {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//
//        if (classLoader.getClass().getName().contains("RestartClassLoader")) {
//            System.out.println("⚠️ DevTools RestartClassLoader detected — disabling caching to avoid classloader conflicts.");
//            return new NoOpCacheManager(); // Disables all caching
//        }
//
//        return null; // allow fallback to real CacheManager
//    }
//}
