package com.kumar.springbootlearning.pattern.factory.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class NotificationServiceImpl implements NotificationService {

    private final String channel;
    private final ChannelConfig config;
    private final ObjectMapper objectMapper;

    public NotificationServiceImpl(String channel, ChannelConfig config, ObjectMapper objectMapper) {
        this.channel = channel;
        this.config = config;
        this.objectMapper = objectMapper;

        log.info("Created NotificationService for channel: {}", channel);
    }

    @Override
    public <T> void sendNotification(T message) {
        log.info("Sending {} message: {}", channel, message);
        // Implement per-channel logic using channel+config
    }

    @Override
    public String getChannel() {
        return this.channel;
    }
}
