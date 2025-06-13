package com.kumar.springbootlearning.messagingstompwebsocket.chat;

import com.kumar.springbootlearning.messagingstompwebsocket.chat.request.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        log.info("send payload: {}",chatMessage);
        return chatMessage;
    }

    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor){
        // add userName in web socket
        simpMessageHeaderAccessor.getSessionAttributes().put("userName", chatMessage.getSender());
        return chatMessage;
    }
}
