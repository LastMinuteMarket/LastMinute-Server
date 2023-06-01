package com.lastminute.lastminuteserver.user.repository;

import com.lastminute.lastminuteserver.user.domain.ForbiddenName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForbiddenNameRepository extends JpaRepository<ForbiddenName, String> {
}
