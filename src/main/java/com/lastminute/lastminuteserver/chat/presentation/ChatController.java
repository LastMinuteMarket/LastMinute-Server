package com.lastminute.lastminuteserver.chat.presentation;

import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import com.lastminute.lastminuteserver.chat.dto.ChatRoomDto;
import com.lastminute.lastminuteserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping()
    public ResponseEntity<ChatRoomDto> createRoom(@RequestParam Long userId, @RequestParam String name){
        ChatRoomDto chatRoomDto = chatService.createRoom(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomDto);
    }

    @GetMapping()
    public ResponseEntity<List<ChatRoom>> findAllRoom(@RequestParam Long userId){
        List<ChatRoom> chatRoomList = chatService.findAllRoom(userId);
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomList);
    }

}
