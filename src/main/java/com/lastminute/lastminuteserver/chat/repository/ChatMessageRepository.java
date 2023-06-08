package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
