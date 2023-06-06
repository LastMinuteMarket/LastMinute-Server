package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
