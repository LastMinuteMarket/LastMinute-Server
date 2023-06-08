package com.lastminute.lastminuteserver.chat.repository;

import com.lastminute.lastminuteserver.chat.domain.User_ChatRoom;
import com.lastminute.lastminuteserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User_ChatRoomRepository extends JpaRepository<User_ChatRoom, Long> {
    Optional<User_ChatRoom> findByUser(User user);
}
