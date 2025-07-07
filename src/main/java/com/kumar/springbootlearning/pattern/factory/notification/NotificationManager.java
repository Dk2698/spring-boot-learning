package com.kumar.springbootlearning.pattern.factory.notification;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class NotificationManager {

    private final Function<String, NotificationServiceImpl> factory;

    public NotificationManager(Function<String, NotificationServiceImpl> factory) {
        this.factory = factory;
    }

    public <T> void send(String channel, T message) {
        NotificationService service = factory.apply(channel);
        if (service == null) {
            throw new IllegalStateException("No handler for channel: " + channel);
        }

        service.sendNotification(message);
    }

//    notificationManager.send("email", new EmailMessage("Welcome", "Hello user!"));
//notificationManager.send("slack", new SlackMessage("#general", "Build completed!"));

}
