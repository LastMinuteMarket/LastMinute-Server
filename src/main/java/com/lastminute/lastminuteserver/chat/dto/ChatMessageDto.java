package com.lastminute.lastminuteserver.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {

    public enum MessageType{
        ENTER, TALK;
    }
    private MessageType messageType;
    private String chatRoomId;
    private String sender;
    private String receiver;
    private String message;
}
