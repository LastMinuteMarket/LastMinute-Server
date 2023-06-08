package com.lastminute.lastminuteserver.user.repository;

import com.lastminute.lastminuteserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    List<User> findAllByAuthenticated(Boolean authenticated);
}
