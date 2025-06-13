package com.kumar.springbootlearning.chat.controller;

import com.kumar.springbootlearning.chat.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat") // Listens to /app/chat
    @SendTo("/topic/messages") // Sends to /topic/messages
    public String sendMessage(String message) throws Exception {
        return "Received: " + message;
    }

    // Sends chat messages to everyone in the chat room
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    // Adds a new user to the chat session
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userName", chatMessage.getSender());
        return chatMessage;
    }
}
