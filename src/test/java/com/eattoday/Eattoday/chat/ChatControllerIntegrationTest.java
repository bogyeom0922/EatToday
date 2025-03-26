package com.eattoday.Eattoday.chat;

import com.eattoday.Eattoday.chat.dto.ChatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerIntegrationTest {

    private static final String WEBSOCKET_URI = "http://localhost:8080/ws";
    private static final String SEND_ENDPOINT = "/app/chat/1"; // roomId = 1
    private static final String SUBSCRIBE_ENDPOINT = "/inquiries/1";

    private WebSocketStompClient stompClient;
    private BlockingQueue<ChatDto> messageQueue;

    @BeforeEach
    public void setup() {
        List transports = List.of(new WebSocketTransport(new StandardWebSocketClient()), new RestTemplateXhrTransport());
        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        messageQueue = new LinkedBlockingDeque<>();
    }

    @Test
    public void testChatMessageProcessing() throws Exception {
        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
                })
                .get(10, TimeUnit.SECONDS);

        session.subscribe(SUBSCRIBE_ENDPOINT, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatDto.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messageQueue.offer((ChatDto) payload);
            }
        });

        ChatDto chatDto = ChatDto.builder()
                .senderId(1L)
                .receiverId(2L)
                .message("test message 2")
                .build();

        session.send(SEND_ENDPOINT, chatDto);

        ChatDto receivedMessage = messageQueue.poll(10, TimeUnit.SECONDS);
        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.getMessage()).isEqualTo("test message 2");
        assertThat(receivedMessage.getSenderId()).isEqualTo(1L);
        assertThat(receivedMessage.getReceiverId()).isEqualTo(2L);
        assertThat(receivedMessage.getRoomId()).isEqualTo(1L);
    }
}