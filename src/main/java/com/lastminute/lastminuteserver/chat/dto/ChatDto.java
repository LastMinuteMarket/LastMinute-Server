package com.lastminute.lastminuteserver.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ChatDto {
    private String roomId;
    private String sender;
    @Setter
    private String message;
}
