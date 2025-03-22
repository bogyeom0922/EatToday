package com.eattoday.Eattoday.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

@Controller
public class ChatController {

    @MessageMapping("/inquiry")
    @SendTo("/inquiries/room1")
    public String handleInquiry(String message) throws Exception {

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        System.out.println("Received message: " + message);
        return new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    @GetMapping("/chat")
    public String getChatPage() {
        return "chat/chat";
    }

}
