package com.lastminute.lastminuteserver.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private String chatRoomId;
    private String sender;
    private String message;
    private String sort; // ENTER, CHAT TODO: Enum 으로 분리
}
