package com.lastminute.lastminuteserver.review.presentation;

import com.lastminute.lastminuteserver.review.dto.ReviewResponseDto;
import com.lastminute.lastminuteserver.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {
    private final ReviewService reviewService;

    @GetMapping(value = "{productId}/review")
    public ResponseEntity<List<ReviewResponseDto>> getReviewListByProduct(@PathVariable("productId") Long productId){
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewListByProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    @DeleteMapping(value = "{productId}/review/{reviewId}")
    public ResponseEntity<Object> deleteReview(@PathVariable("productId") Long productId,
                                               @PathVariable("reviewId") Long reviewId,
                                               Long userId){
        reviewService.deleteReview(productId, reviewId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
