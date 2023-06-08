package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String > {
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query("select c from chatroom where :user_chatRoom in c.userChatRoomList")
//    List<ChatRoom> findByUserChatRoomList(User_ChatRoom user_chatRoom);

    @Override
    List<ChatRoom> findAll();
}
