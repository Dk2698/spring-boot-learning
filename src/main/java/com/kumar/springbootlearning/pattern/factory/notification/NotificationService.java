package com.kumar.springbootlearning.pattern.factory.notification;

public interface NotificationService {
    <T> void sendNotification(T message);
//    <T> void sendNotification(T message) throws NotificationException;
    String getChannel();
}

