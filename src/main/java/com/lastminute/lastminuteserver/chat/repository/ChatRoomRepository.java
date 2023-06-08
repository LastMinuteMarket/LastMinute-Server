package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
