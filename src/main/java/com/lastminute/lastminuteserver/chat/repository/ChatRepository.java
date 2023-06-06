package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
