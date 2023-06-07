package com.lastminute.lastminuteserver.review.presentation;

import com.lastminute.lastminuteserver.review.dto.ReviewRequestDto;
import com.lastminute.lastminuteserver.review.dto.ReviewResponseDto;
import com.lastminute.lastminuteserver.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping(value = "/{productId}/create")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("productId") Long productId,
                                                          @RequestParam Long userId,
                                                          @Valid ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.createReview(productId, userId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    @GetMapping(value = "/{productId}/all")
    public ResponseEntity<List<ReviewResponseDto>> getReviewListByProduct(@PathVariable("productId") Long productId){
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewListByProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    @GetMapping(value = "/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable("productId") Long productId,
                                                       @PathVariable("reviewId") Long reviewId){
        ReviewResponseDto reviewResponseDto = reviewService.getReview(productId, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @PutMapping(value = "/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable("productId") Long productId,
                                                          @PathVariable("reviewId") Long reviewId,
                                                          @RequestParam Long userId,
                                                          @Valid ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.updateReview(productId, reviewId, userId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(reviewResponseDto);
    }

    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<Object> deleteReview(@PathVariable("productId") Long productId,
                                               @PathVariable("reviewId") Long reviewId,
                                               @RequestParam Long userId){
        reviewService.deleteReview(productId, reviewId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
