package com.lastminute.lastminuteserver.review.repository;

import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.review.domain.Review;
import com.lastminute.lastminuteserver.review.dto.ReviewResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    Optional<Review> findById(@Param("id") Long id);

    List<Review> findByProduct(Product product);
}
