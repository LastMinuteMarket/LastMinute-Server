package com.lastminute.lastminuteserver.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ChatMessageDto {
    private String chatRoomId;
    private String sender;
    private String receiver;
    private String message;
    private String sort; // ENTER, CHAT TODO: Enum 으로 분리
}
