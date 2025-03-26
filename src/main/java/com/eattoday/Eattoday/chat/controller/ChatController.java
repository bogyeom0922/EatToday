package com.eattoday.Eattoday.chat.controller;

import com.eattoday.Eattoday.chat.dto.ChatDto;
import com.eattoday.Eattoday.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/inquiry")
    @SendTo("/inquiries/room1")
    public String handleInquiry(String message) throws Exception {

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        System.out.println("Received message: " + message);
        return new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/inquiries/{roomId}")
    public ChatDto processMessage(@DestinationVariable("roomId") Long roomId, ChatDto chatDto) {
        chatDto.setRoomId(roomId);
        return chatService.saveChatMessage(chatDto);
    }

    @GetMapping("/chat")
    public String getChatPage() {
        return "chat/chat";
    }

}
