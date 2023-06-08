package com.lastminute.lastminuteserver.chat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lastminute.lastminuteserver.chat.dto.ChatMessageDto;
import com.lastminute.lastminuteserver.chat.service.ChatService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
@Entity
public class ChatRoom {

    @Id
    private String id;

    @NotNull
    private String name;

    @Setter
    @NotNull
    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom")
    private List<User_ChatRoom> userChatRoomList = new ArrayList<>();

    @JsonIgnore
    @Transient
    private Set<WebSocketSession> sessionSet = new HashSet<>();

    @Setter
    @NotNull
    private LocalDateTime recentlySentAt = LocalDateTime.now();

    @Builder
    public ChatRoom(String id, String name){
        this.id = id;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatMessageDto dto, ChatService chatService){
        if (dto.getSort().equals("ENTER")){
            sessionSet.add(session);
            dto.setMessage("[채팅이 시작되었어요] "+dto.getMessage());
        }
        sendMessage(dto, chatService);
    }

    public <T> void sendMessage(ChatMessageDto message, ChatService chatService){
        sessionSet.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
