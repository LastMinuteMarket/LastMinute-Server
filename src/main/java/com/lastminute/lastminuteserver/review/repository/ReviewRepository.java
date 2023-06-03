package com.lastminute.lastminuteserver.review.repository;

import com.lastminute.lastminuteserver.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    Optional<Review> findById(@Param("id") Long id);
}
