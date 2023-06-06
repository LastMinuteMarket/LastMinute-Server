package com.lastminute.lastminuteserver.chat.domain.entity;

import com.lastminute.lastminuteserver.chat.dto.ChatDto;
import com.lastminute.lastminuteserver.chat.service.ChatService;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User Buyer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User Seller;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Set<WebSocketSession> sessionSet = new HashSet<>();

    public void handleAction(WebSocketSession session, ChatDto dto, ChatService chatService){
        sessionSet.add(session);
        dto.setMessage(dto.getMessage());
        sendMessage(dto, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService){
        sessionSet.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
