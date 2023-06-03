package com.lastminute.lastminuteserver.review.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
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
        validateProduct(productId);
        return null;
    }

    public List<ReviewResponseDto> getReviewListByProduct(Long productId){
        validateProduct(productId);
        return null;
    }

    public ReviewResponseDto getReview(Long reviewId){
        valiedateReview(reviewId);
        return null;
    }

    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto reviewRequestDto){
        valiedateReview(reviewId);
        return null;
    }

    public void deleteReview(Long productId, Long reviewId){
        valiedateReview(reviewId);
    }

    private Review valiedateReview(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.REVIEW_NOT_FOUND));
    }

    private Product validateProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PRODUCT_NOT_FOUND));
    }
}
