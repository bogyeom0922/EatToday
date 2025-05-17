package com.eattoday.Eattoday.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private Long roomId;
    private Long senderId;
    private Long receiverId;
    private String message;
}