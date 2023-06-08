package com.lastminute.lastminuteserver.chat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_chatroom")
@Entity
public class User_ChatRoom { // 유저가 소속된 채팅방(다대다 관계 해소를 위한 테이블)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;
}
