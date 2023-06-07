package com.lastminute.lastminuteserver.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.lastminuteserver.chat.dto.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoom findByRoomId(String roomId){
        return chatRoomMap.get(roomId);
    }

    public ChatRoom createRoom(){
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                        .id(randomId)
                        .build();
        chatRoomMap.put(randomId, chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message){
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
