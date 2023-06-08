package com.lastminute.lastminuteserver.chat.dto;

import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomDto {
    private String id;
    private String name;
    @Nullable
    private String user1;
    @Nullable
    private String user2;
    private LocalDateTime recentlySentAt;

    public static ChatRoomDto from(ChatRoom chatRoom){
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .user1(chatRoom.getUserChatRoomList().get(0).getUser().getNickname())
                .user2(chatRoom.getUserChatRoomList().get(1).getUser().getNickname())
                .recentlySentAt(chatRoom.getRecentlySentAt())
                .build();
    }
}
