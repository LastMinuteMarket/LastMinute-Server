package com.lastminute.lastminuteserver.review.presentation;

import com.lastminute.lastminuteserver.review.dto.ReviewRequestDto;
import com.lastminute.lastminuteserver.review.dto.ReviewResponseDto;
import com.lastminute.lastminuteserver.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping(value = "/{productId}/review")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("productId") Long productId,
                                                          Long userId,
                                                          @Valid ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.createReview(productId, userId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    @GetMapping(value = "/{productId}/review")
    public ResponseEntity<List<ReviewResponseDto>> getReviewListByProduct(@PathVariable("productId") Long productId){
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewListByProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    @GetMapping(value = "/{productId}/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable("productId") Long productId,
                                                       @PathVariable("reviewId") Long reviewId){
        ReviewResponseDto reviewResponseDto = reviewService.getReview(productId, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @PutMapping(value = "/{productId}/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable("productId") Long productId,
                                                          @PathVariable("reviewId") Long reviewId,
                                                          Long userId,
                                                          @Valid ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.updateReview(productId, reviewId, userId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(reviewResponseDto);
    }

    @DeleteMapping(value = "/{productId}/review/{reviewId}")
    public ResponseEntity<Object> deleteReview(@PathVariable("productId") Long productId,
                                               @PathVariable("reviewId") Long reviewId,
                                               Long userId){
        reviewService.deleteReview(productId, reviewId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
