package com.lastminute.lastminuteserver.chat.dto;

import com.lastminute.lastminuteserver.chat.domain.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime localDateTime;

}
