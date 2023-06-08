package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    @Modifying(flushAutomatically = true, clearAutomatically = false)
//    @Query("select c from chatMessage where c.sender = :user or c.receiver = :user")
//    List<ChatMessage> findAllBySenderOrReceiver(User user);

    @Override
    List<ChatMessage> findAll();
}
