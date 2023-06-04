package com.lastminute.lastminuteserver.review.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.repository.ProductRepository;
import com.lastminute.lastminuteserver.review.repository.ReviewRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewService reviewService;

    Long productId = 1L;
    Long reviewId = 1L;

    @Disabled
    @Test
    @DisplayName("DB의 review 테이블에 없는 reviewId일 경우 RequestException 예외를 리턴한다")
    public void raiseReviewRequestException(){
        // given
        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(null));
        given(reviewRepository.findById(reviewId)).willReturn(Optional.ofNullable(null));
        // when, then
        assertThatThrownBy(() -> reviewService.getReview(productId, reviewId))
                .isInstanceOf(RequestException.class);
    }

    @Test
    @DisplayName("DB의 product 테이블에 없는 productId 경우 RequestException 예외를 리턴한다")
    public void raiseProductRequestException(){
        // given
        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(null));
        // when, then
        assertThatThrownBy(() -> reviewService.getReviewListByProduct(productId))
                .isInstanceOf(RequestException.class);
    }
}