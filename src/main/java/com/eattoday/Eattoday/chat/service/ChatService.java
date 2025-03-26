package com.eattoday.Eattoday.chat.service;

import com.eattoday.Eattoday.chat.domain.Chat;
import com.eattoday.Eattoday.chat.dto.ChatDto;
import com.eattoday.Eattoday.chat.repository.ChatRepository;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatDto saveChatMessage(ChatDto chatDto) {
        User sender = userRepository.findById(chatDto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found with id: " + chatDto.getSenderId()));
        User receiver = userRepository.findById(chatDto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found with id: " + chatDto.getReceiverId()));

        Chat chat = Chat.builder()
                .roomId(chatDto.getRoomId())
                .sender(sender)
                .receiver(receiver)
                .message(chatDto.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        Chat savedChat = chatRepository.save(chat);

        return ChatDto.builder()
                .roomId(savedChat.getRoomId())
                .senderId(savedChat.getSender().getId())
                .receiverId(savedChat.getReceiver().getId())
                .message(savedChat.getMessage())
                .build();
    }
}