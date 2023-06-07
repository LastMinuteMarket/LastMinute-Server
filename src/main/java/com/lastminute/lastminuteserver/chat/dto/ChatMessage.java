package com.lastminute.lastminuteserver.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private String roomId;
    private String sender;
    private String message;
    private String sort;
}
