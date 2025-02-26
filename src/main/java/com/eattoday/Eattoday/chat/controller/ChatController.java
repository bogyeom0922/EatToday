package com.eattoday.Eattoday.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @MessageMapping("/inquiry")
    @SendTo("/inquiries/{room}")
    public String sendInquiry(@DestinationVariable String room, String message) {
        return "답변: " + message;
    }

    @GetMapping("/chat")
    public String getChatPage() {
        return "chat/chat";
    }

}
