package com.lastminute.lastminuteserver.chat.presentation;

import com.amazonaws.Response;
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
        ChatRoomDto chatRoomDto = chatService.createRoom(userId, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomDto);
    }

    @GetMapping()
    public ResponseEntity<List<ChatRoomDto>> findAllRoom(@RequestParam Long userId){
        List<ChatRoomDto> chatRoomDtoList = chatService.findAllRoom(userId);
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomDtoList);
    }

}
