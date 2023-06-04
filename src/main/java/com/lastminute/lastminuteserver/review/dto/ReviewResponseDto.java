package com.lastminute.lastminuteserver.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lastminute.lastminuteserver.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewResponseDto {
    private String title;

    private String content;

    private String userNickName;

    private Long productId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    public static ReviewResponseDto from(Review review){
        return ReviewResponseDto.builder()
                .title(review.getTitle())
                .content(review.getContent())
                .userNickName(review.getUser().getNickname())
                .productId(review.getProduct().getId())
                .build();
    }
}
