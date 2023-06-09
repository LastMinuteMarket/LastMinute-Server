package com.lastminute.lastminuteserver.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.lastminuteserver.chat.domain.ChatMessage;
import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import com.lastminute.lastminuteserver.chat.dto.ChatMessageDto;
import com.lastminute.lastminuteserver.chat.dto.ChatRoomDto;
import com.lastminute.lastminuteserver.chat.repository.ChatMessageRepository;
import com.lastminute.lastminuteserver.chat.repository.ChatRoomRepository;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private Map<String, ChatRoomDto> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(Long userId){
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
        return chatMessageRepository.findAll().stream()
                .filter(c1 -> user.equals(c1.getReceiver()) || user.equals(c1.getSender()))
                .map(c2 -> c2.getChatRoom())
                .distinct()
                .collect(Collectors.toList());
    }

    public ChatRoomDto findByChatRoomId(String chatRoomId){
        return chatRoomMap.get(chatRoomId);
    }

    public ChatRoomDto createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                        .chatRoomId(randomId)
                        .name(name)
                        .build();
        ChatRoom chatRoom = chatRoomDto.toEntity();
        chatRoomRepository.saveAndFlush(chatRoom);
        chatRoomMap.put(randomId, chatRoomDto);
        return chatRoomDto;
    }


    public <T> void sendMessage(WebSocketSession session, ChatMessageDto chatMessageDto){
        try {
            ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                    .orElseThrow(() -> RequestException.of(RequestExceptionCode.CHATROOM_NOT_FOUND));
            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoom(chatRoom)
                    .sender(getUser(chatMessageDto.getSender()))
                    .receiver(getUser(chatMessageDto.getReceiver()))
                    .message(chatMessageDto.getMessage())
                    .build();
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessageDto)));

            chatRoom.setReceiver(getUser(chatMessageDto.getReceiver()).getNickname());
            chatRoom.setRecentMessage(chatMessage.getMessage());
            chatRoom.setRecentlySentAt(LocalDateTime.now());
            chatRoomRepository.saveAndFlush(chatRoom);
            chatMessageRepository.saveAndFlush(chatMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUser(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }

}
