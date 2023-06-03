package com.lastminute.lastminuteserver.review.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("DB의 review 테이블에 없는 reviewId일 경우 RequestException 예외 리턴")
    public void raiseReviewRequestException(){
        Long reviewId = 1L;
        // given
        given(reviewRepository.findById(reviewId)).willReturn(Optional.ofNullable(null));
        // when, then
        assertThatThrownBy(() -> reviewService.deleteReview(reviewId))
                .isInstanceOf(RequestException.class);
    }

    @Test
    @DisplayName("DB의 product 테이블에 없는 productId 경우 RequestException 예외 리턴")
    public void raiseProductRequestException(){
        Long productId = 1L;
        // given
        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(null));
        // when, then
        assertThatThrownBy(() -> reviewService.getReviewListByProduct(productId))
                .isInstanceOf(RequestException.class);
    }
}