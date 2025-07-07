package com.kumar.springbootlearning.pattern.factory.notification;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NotificationServiceFactory {
    private final Function<String, NotificationServiceImpl> factory;

    public NotificationServiceFactory(Function<String, NotificationServiceImpl> factory) {
        this.factory = factory;
    }

    public NotificationService get(String channel) {
        return factory.apply(channel);
    }
}

