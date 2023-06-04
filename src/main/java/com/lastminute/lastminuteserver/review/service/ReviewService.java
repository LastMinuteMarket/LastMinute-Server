package com.lastminute.lastminuteserver.review.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.review.domain.Review;
import com.lastminute.lastminuteserver.review.dto.ReviewRequestDto;
import com.lastminute.lastminuteserver.review.dto.ReviewResponseDto;
import com.lastminute.lastminuteserver.review.repository.ReviewRepository;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto createReview(Long productId, Long userId, ReviewRequestDto reviewRequestDto){
        Product product = validateProduct(productId);
        User user = getUser(userId);

        Review review = reviewRepository.save(
                Review.builder()
                        .title(reviewRequestDto.getTitle())
                        .content(reviewRequestDto.getContent())
                        .user(user)
                        .product(product)
                        .build()
        );
        return ReviewResponseDto.from(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewListByProduct(Long productId){
        Product product = validateProduct(productId);
        List<Review> reviewList = reviewRepository.findByProduct(product);
        return reviewList.stream()
                .map(review -> ReviewResponseDto.from(review))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(Long reviewId){
        Review review = valiedateReview(reviewId);
        return ReviewResponseDto.from(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, Long userId, ReviewRequestDto reviewRequestDto){
        Review review = valiedateReview(reviewId);
        User user = getUser(userId);

        if (!review.getUser().equals(user)){
            // TODO: 전역 예외처리
            throw new RuntimeException();
        }

        review.setTitle(reviewRequestDto.getTitle());
        review.setContent(reviewRequestDto.getContent());

        return ReviewResponseDto.from(review);
    }

    @Transactional
    public void deleteReview(Long productId, Long reviewId, Long userId){
        valiedateReview(productId);
        Review review = valiedateReview(reviewId);
        User user = getUser(userId);

        if (!review.getUser().equals(user)){
            // TODO: 전역 예외처리
            throw new RuntimeException();
        }

        reviewRepository.deleteById(reviewId);
    }

    private Review valiedateReview(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.REVIEW_NOT_FOUND));
    }

    private Product validateProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.PRODUCT_NOT_FOUND));
    }

    private User getUser(Long userId){
        return userRepository.findById(userId).get();
    }
}
