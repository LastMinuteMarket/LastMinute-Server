package com.lastminute.lastminuteserver.chat.dto;

import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import com.lastminute.lastminuteserver.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoomDto {
    private String chatRoomId;
    private String name;
    private Set<WebSocketSession> sessionSet = new HashSet<>();

    @Builder
    public ChatRoomDto(String chatRoomId, String name){
        this.chatRoomId = chatRoomId;
        this.name = name;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .id(this.getChatRoomId())
                .name(this.getName())
                .build();
    }

    public static ChatRoomDto from(ChatRoom chatRoom){
        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getId())
                .name(chatRoom.getName())
                .build();
    }

    public void handleAction(WebSocketSession session, ChatMessageDto chat, ChatService chatService){
        if (chat.getMessageType().equals(ChatMessageDto.MessageType.ENTER)){
            sessionSet.add(session);
            chat.setMessage("[채팅이 시작되었어요] "+chat.getMessage());
        }
        chat.setLocalDateTime(LocalDateTime.now());
        sendMessage(chat, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessionSet.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
