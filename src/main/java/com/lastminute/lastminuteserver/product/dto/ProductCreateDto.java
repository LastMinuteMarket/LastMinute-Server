package com.lastminute.lastminuteserver.product.dto;

import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ProductCreateDto {

    @NotNull(message = "사용처 주소 정보를 입력해주세요.")
    private final PlacementDto placement;

    @NotNull(message = "상품 상세 정보를 입력해주세요.")
    private final ProductDetailDto detail;
}
