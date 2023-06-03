package com.lastminute.lastminuteserver.review.repository;

import com.lastminute.lastminuteserver.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
