package com.lastminute.lastminuteserver.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PriceScheduleDto(
        @NotNull(message = "가격을 적용할 날짜를 입력해주세요.")
        LocalDate applyAt,

        @NotNull
        @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
        Integer price
) {
}
