package com.lastminute.lastminuteserver.review.service;

import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.review.domain.Review;
import com.lastminute.lastminuteserver.review.dto.ReviewRequestDto;
import com.lastminute.lastminuteserver.review.dto.ReviewResponseDto;
import com.lastminute.lastminuteserver.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewResponseDto createReview(Long productId, ReviewRequestDto reviewRequestDto){
        return null;
    }

    public List<ReviewResponseDto> getReviewListByProduct(Long productId){
        return null;
    }

    public ReviewResponseDto getReview(Long reviewId){
        return null;
    }

    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto reviewRequestDto){
        return null;
    }

    public void deleteReview(Long reviewId){
    }

    public Review valiedateReview(Long reviewId){
        return null;
    }

    public Product validateProduct(Long productId){
        return null;
    }
}
