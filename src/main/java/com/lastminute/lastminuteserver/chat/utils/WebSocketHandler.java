package com.lastminute.lastminuteserver.chat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.lastminuteserver.chat.dto.ChatRoom;
import com.lastminute.lastminuteserver.chat.dto.ChatMessage;
import com.lastminute.lastminuteserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chat = objectMapper.readValue(payload, ChatMessage.class);
        log.info(chat.getMessage());
        log.info(chat.getSender());
        log.info(chat.getChatRoomId());
        log.info(chat.getSort());
        ChatRoom chatRoom = chatService.findByChatRoomId(chat.getChatRoomId());
        chatRoom.handleAction(session, chat, chatService);
    }
}
