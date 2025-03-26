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

    @GetMapping("/chat")
    public String getChatPage() {
        return "chat/chat";
    }

    @MessageMapping("/inquiry")
    @SendTo("/inquiries/room1")
    public ChatDto handleInquiry(ChatDto chatDto) throws Exception {
        if (chatDto == null || chatDto.getMessage() == null || chatDto.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        // roomId가 null이면 기본값 1L로 설정
        if (chatDto.getRoomId() == null) {
            chatDto.setRoomId(1L);
        }
        System.out.println("Received ChatDto: " + chatDto);
        return chatService.saveChatMessage(chatDto);
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/inquiries/{roomId}")
    public ChatDto processMessage(@DestinationVariable("roomId") Long roomId, ChatDto chatDto) {
        chatDto.setRoomId(roomId);
        return chatService.saveChatMessage(chatDto);
    }


}
