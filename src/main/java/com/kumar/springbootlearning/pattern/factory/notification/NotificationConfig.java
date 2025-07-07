package com.kumar.springbootlearning.pattern.factory.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class NotificationConfig {

    @Bean
    public Function<String, NotificationServiceImpl> notificationFactory(ChannelConfig config, ObjectMapper objectMapper) {
        return channel -> {
            try {
                return new NotificationServiceImpl(channel, config, objectMapper);
            } catch (Exception e) {
                log.error("Unable to initialize notification service for {}: {}", channel, e.getMessage());
                return null;
            }
        };
    }
}
