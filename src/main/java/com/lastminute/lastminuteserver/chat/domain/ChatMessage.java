package com.lastminute.lastminuteserver.chat.domain;

import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @NotNull
    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    @Builder
    public ChatMessage(ChatRoom chatRoom,
                       User sender,
                       User receiver,
                       String message){
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }
}
