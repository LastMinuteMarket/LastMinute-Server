package com.lastminute.lastminuteserver.product.dto;

import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record ProductCreateDto (

    @NotNull(message = "사용처 주소 정보를 입력해주세요.")
    PlacementDto placement,

    @NotNull(message = "상품 상세 정보를 입력해주세요.")
    ProductDetailDto detail,

    @Nullable
    List<PriceScheduleDto> priceSchedules
) {

}