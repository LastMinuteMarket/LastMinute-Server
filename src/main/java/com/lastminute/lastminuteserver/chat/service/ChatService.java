package com.lastminute.lastminuteserver.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.lastminuteserver.chat.domain.ChatMessage;
import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import com.lastminute.lastminuteserver.chat.domain.User_ChatRoom;
import com.lastminute.lastminuteserver.chat.dto.ChatMessageDto;
import com.lastminute.lastminuteserver.chat.dto.ChatRoomDto;
import com.lastminute.lastminuteserver.chat.repository.ChatMessageRepository;
import com.lastminute.lastminuteserver.chat.repository.ChatRoomRepository;
import com.lastminute.lastminuteserver.chat.repository.User_ChatRoomRepository;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import com.lastminute.lastminuteserver.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final User_ChatRoomRepository userChatRoomRepository;

    public List<ChatRoomDto> findAllRoom(Long userId){
        User user = getUser(userId);
        log.info("findAllRoom 의 메서드"+userChatRoomRepository.findAll().size());
        User_ChatRoom user_chatRoom = userChatRoomRepository.findByUser(user)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.CHATROOM_NOT_FOUND));
        List<ChatRoomDto> chatRoomList = chatRoomRepository.findAll().stream()
                .filter(chatRoom -> chatRoom.getUserChatRoomList().contains(user_chatRoom) == true)
                .map(chatRoom -> ChatRoomDto.from(chatRoom))
                .collect(Collectors.toList());
//        return chatRoomRepository.findByUserChatRoomList(user_chatRoom);
        return chatRoomList;
    }

    public ChatRoom findByChatRoomId(String chatRoomId){
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.CHATROOM_NOT_FOUND));
    }

    public ChatRoomDto createRoom(Long userId, String name){
        userService.authenticate(userId);
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                        .id(randomId)
                        .name(name)
                        .build();
        chatRoom = chatRoomRepository.saveAndFlush(chatRoom);
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .recentlySentAt(chatRoom.getRecentlySentAt())
                .build();
    }

    @Transactional
    public <T> void sendMessage(WebSocketSession session, ChatMessageDto chatMessageDto){
        try {
            // 엔티티 get
            User sender = getUser(chatMessageDto.getSender());
            ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                    .orElseThrow(() -> RequestException.of(RequestExceptionCode.CHATROOM_NOT_FOUND));
//            User receiver = chatRoom.getUserChatRoomList().stream()
//                    .filter(participant -> !participant.getUser().equals(sender))
//                    .collect(Collectors.toList())
//                    .get(0).getUser();
            User receiver = getUser(chatMessageDto.getReceiver());

            // chatRoom 에 참여자 추가, 가장 최근 대화 시간 update
            List<User_ChatRoom> userChatRoomList = new ArrayList<>();
            User_ChatRoom forSender = User_ChatRoom.builder()
                    .user(sender)
                    .chatRoom(chatRoom)
                    .build();
            User_ChatRoom forReceiver = User_ChatRoom.builder()
                    .user(receiver)
                    .chatRoom(chatRoom)
                    .build();
            userChatRoomList.add(forSender);
            userChatRoomList.add(forReceiver);

            userChatRoomRepository.saveAllAndFlush(userChatRoomList);
            chatRoom.setUserChatRoomList(userChatRoomList);
            chatRoom.setRecentlySentAt(LocalDateTime.now());

            // chatMessage 생성
            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoom(chatRoom)
                    .sender(sender)
                    .receiver(receiver)
                    .message(chatMessageDto.getMessage())
                    .sort(chatMessageDto.getSort())
                    .build();
            chatMessageRepository.saveAndFlush(chatMessage);
            log.info("chat 메시지 생성이 완료되었어요");
            log.info("chatRoom의 참여자들");
            chatRoom.getUserChatRoomList().stream().forEach(
                    participant -> log.info(participant.getUser().getNickname())
            );
            log.info("chatRoom의 가장 최근 대화 시간"+chatRoom.getRecentlySentAt());
            log.info("chatMessage 보냄"+chatMessage.getSender().getNickname());
            log.info("chatMessage 받음"+chatMessage.getReceiver().getNickname());

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessageDto)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUser(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }

    public User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }
}
