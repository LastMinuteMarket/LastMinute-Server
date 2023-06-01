package com.lastminute.lastminuteserver.user.repository;

import com.lastminute.lastminuteserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
