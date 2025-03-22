package com.eattoday.Eattoday.chat;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest {

    private static final String WEBSOCKET_URI = "http://localhost:8080/ws";
    private static final String SUBSCRIBE_CHANNEL = "/inquiries/room1";
    private static final String SEND_CHANNEL = "/app/inquiry";

    private WebSocketStompClient stompClient;
    private BlockingQueue<String> messageQueue;

    @BeforeEach
    void setup() {
        List<Transport> transports = List.of(new WebSocketTransport(new StandardWebSocketClient()), new RestTemplateXhrTransport());
        SockJsClient sockJsClient = new SockJsClient(transports);

        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        messageQueue = new LinkedBlockingDeque<>();
    }

    @Test
    void testWebSocketMessaging() throws Exception {
        StompSession session = stompClient
                .connectAsync(WEBSOCKET_URI, new CustomStompSessionHandler())
                .get(10, TimeUnit.SECONDS);


        session.subscribe(SUBSCRIBE_CHANNEL, new CustomStompFrameHandler());


        String testMessage = "WebSocket Test Message";
        session.send(SEND_CHANNEL, testMessage);


        String receivedMessage = messageQueue.poll(10, TimeUnit.SECONDS);
        assertThat(receivedMessage).isEqualTo(testMessage);
        System.out.println("received Message: " + receivedMessage);
        System.out.println("test Message: " + testMessage);
        System.out.println("수신한 메시지가 보낸 메시지와 동일합니다.");
    }


    private static class CustomStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            Assertions.fail("WebSocket 예외 발생: " + exception.getMessage());
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            Assertions.fail("WebSocket 전송 오류: " + exception.getMessage());
        }
    }


    private class CustomStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            if (payload instanceof String message) {
                boolean success = messageQueue.offer(message);
                if (!success) {
                    Assertions.fail("메시지를 큐에 추가하지 못함: " + message);
                }
            }
        }
    }
}