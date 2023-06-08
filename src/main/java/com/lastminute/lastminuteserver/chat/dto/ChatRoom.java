package com.lastminute.lastminuteserver.chat.dto;

import com.lastminute.lastminuteserver.chat.service.ChatService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    private String id;

    private Set<WebSocketSession> sessionSet = new HashSet<>();

    @Builder
    public ChatRoom(String id){
        this.id = id;
    }

    public void handleAction(WebSocketSession session, ChatMessage dto, ChatService chatService){
        if (dto.getSort().equals("ENTER")){
            sessionSet.add(session);
            dto.setMessage(dto.getMessage()+" 채팅이 시작되었어요");
        }
        sendMessage(dto, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessionSet.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
