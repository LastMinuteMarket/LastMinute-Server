package com.lastminute.lastminuteserver.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ProductEditDto (
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
    String title,

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 1_000, message = "내용은 최대 1000자까지 입력할 수 있습니다.")
    String content
) {

}
