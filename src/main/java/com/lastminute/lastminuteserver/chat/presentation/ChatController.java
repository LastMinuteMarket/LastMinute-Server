package com.lastminute.lastminuteserver.chat.presentation;

import com.lastminute.lastminuteserver.chat.dto.ChatRoom;
import com.lastminute.lastminuteserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping()
    public ChatRoom createRoom(){
        return chatService.createRoom();
    }

    @GetMapping()
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
}
