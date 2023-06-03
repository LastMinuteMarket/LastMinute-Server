package com.lastminute.lastminuteserver.review.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
    @Size(min = 5, max = 100, message = "5~100 글자 사이로 입력해야 합니다.")
    private String title;
    @Size(min = 5, max = 1000, message = "5~1000 글자 사이로 입력해야 합니다.")
    private String content;
}
