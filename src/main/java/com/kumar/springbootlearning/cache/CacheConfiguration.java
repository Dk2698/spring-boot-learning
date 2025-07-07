package com.kumar.springbootlearning.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
@Slf4j
//@EnableConfigurationProperties(ComCacheProperties.class)
@ConditionalOnProperty(prefix = "com.cache", name = "enabled", havingValue = "true")
public class CacheConfiguration {

    @Bean
    public Map<String, RedisConnectionFactory> redisConnectionFactories(ComCacheProperties cacheProperties) {
        final Map<String, RedisConnectionFactory> redisFactories = new HashMap<>();

        Map<String, CacheManagerProperties> redisCaches = filterCaches(cacheProperties.getCaches(), CacheProvider.REDIS);

        redisCaches.forEach((key, value) -> {
            if (StringUtils.hasText(value.getRemoteHost()) && value.getPort() > 0) {
                RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(value.getRemoteHost(), value.getPort());
                LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config);
                connectionFactory.afterPropertiesSet(); // Ensures it is initialized
                redisFactories.put(key, connectionFactory);
            } else {
                log.error("Missing host or port information for {} redis cache. No cache will be initialized", key);
            }
        });
        return redisFactories;
    }

    @Bean
    public CacheManager cacheManager(ComCacheProperties cacheProperties, Map<String, RedisConnectionFactory> redisConnectionFactories) {
        List<CacheManager> cacheManagers = new ArrayList<>();
        Map<String, CacheManagerProperties> caffeineCaches = filterCaches(cacheProperties.getCaches(), CacheProvider.CAFFEINE);
        if (!caffeineCaches.isEmpty()) {
            CaffeineCacheManager caffeineCacheManager = buildCaffeineCacheManager(caffeineCaches);
            cacheManagers.add(caffeineCacheManager);
        }
        Map<String, CacheManagerProperties> redisCaches = filterCaches(cacheProperties.getCaches(), CacheProvider.REDIS);
        redisCaches.forEach((key, value) -> {
            if (redisConnectionFactories.containsKey(key)) {
                cacheManagers.add(buildRedisCacheManager(value, redisConnectionFactories.get(key)));
            } else {
                log.error("Unable to find RedisConnectionFactory for {}. Skipping...", key);
            }
        });
        return new CompositeCacheManager(cacheManagers.toArray(new CacheManager[0]));
    }

    private CaffeineCacheManager buildCaffeineCacheManager(Map<String, CacheManagerProperties> caches) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        caches.forEach((key, value) -> {
            Cache<Object, Object> cache = buildCaffeineCache(value);
            for (String cacheName : value.getCacheNames()) {
                cacheManager.registerCustomCache(cacheName, cache);
            }
        });
        return cacheManager;
    }

    private Map<String, CacheManagerProperties> filterCaches(Map<String, CacheManagerProperties> allCaches, CacheProvider provider) {
        if (allCaches != null) {
            return allCaches.entrySet().stream()
                    .filter(entry -> entry.getValue().getProvider() == provider)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return new HashMap<>();
    }

    private RedisCacheManager buildRedisCacheManager(CacheManagerProperties cacheManagerProperties, RedisConnectionFactory connectionFactory) {
        if (connectionFactory != null) {
            RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig();
            if (cacheManagerProperties.getTtlAfterWrite() > 0) {
                cacheConfig.entryTtl(Duration.ofSeconds(cacheManagerProperties.getTtlAfterWrite()));
            }
            if (cacheManagerProperties.getTtlAfterAccess() > 0) {
                // TODO - requires upgrade to 3.2
                log.warn("redis does not support expiry after access, will be ignored");
            }
            cacheConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

            return RedisCacheManager.builder(connectionFactory)
                    .cacheDefaults(cacheConfig)
                    .initialCacheNames(Set.of(cacheManagerProperties.getCacheNames()))
                    .build();
        }
        return null;
    }

    private Cache<Object, Object> buildCaffeineCache(CacheManagerProperties cacheManagerProperties) {
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (cacheManagerProperties.getInitialCapacity() > 0) {
            builder.initialCapacity(cacheManagerProperties.getInitialCapacity());
        }
        if (cacheManagerProperties.getMaximumSize() > 0) {
            builder.maximumSize(cacheManagerProperties.getMaximumSize());
        }
        if (cacheManagerProperties.getTtlAfterAccess() > 0) {
            builder.expireAfterAccess(cacheManagerProperties.getTtlAfterAccess(), TimeUnit.SECONDS);
        }
        if (cacheManagerProperties.getTtlAfterWrite() > 0) {
            builder.expireAfterWrite(cacheManagerProperties.getTtlAfterWrite(), TimeUnit.SECONDS);
        }
        return builder.evictionListener((Object key, Object value, RemovalCause cause) ->
                        log.debug(String.format("Key %s was evicted (%s)%n", key, cause)))
                .removalListener((Object key, Object value, RemovalCause cause) ->
                        log.debug(String.format("Key %s was removed (%s)%n", key, cause)))
                .build();

    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
