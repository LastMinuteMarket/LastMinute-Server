package com.lastminute.lastminuteserver.chat.service;

import com.lastminute.lastminuteserver.chat.domain.entity.ChatRoom;
import com.lastminute.lastminuteserver.chat.repository.ChatRepository;
import com.lastminute.lastminuteserver.chat.repository.ChatRoomRepository;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    public ChatRoom findByRoomId(Long roomId){
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }

    public ChatRoom createRoom(User buyer, User seller, Product product){
        ChatRoom chatRoom = ChatRoom.builder()
                .Buyer(buyer)
                .Seller(seller)
                .product(product)
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message){
        try {
            session.sendMessage(new TextMessage(message.toString()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
