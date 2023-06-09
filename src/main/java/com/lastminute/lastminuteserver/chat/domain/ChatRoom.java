package com.lastminute.lastminuteserver.chat.domain;

import com.lastminute.lastminuteserver.chat.dto.ChatRoomDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chatroom")
public class ChatRoom {
    @Id
    private String id;

    @NotNull
    private String name;

    private String receiver;

    private String recentMessage;

    private LocalDateTime recentlySentAt;

    @Builder
    public ChatRoom(String id, String name){
        this.id = id;
        this.name = name;
    }

}
