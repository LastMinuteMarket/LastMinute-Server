package com.lastminute.lastminuteserver.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.lastminuteserver.chat.dto.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(Long userId){
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoom findByChatRoomId(String chatRoomId){
        return chatRoomMap.get(chatRoomId);
    }

    public ChatRoom createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                        .chatRoomId(randomId)
                        .name(name)
                        .build();
        chatRoomMap.put(randomId, chatRoom);
        return chatRoom;
    }


    public <T> void sendMessage(WebSocketSession session, T chatMessageDto){
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessageDto)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
