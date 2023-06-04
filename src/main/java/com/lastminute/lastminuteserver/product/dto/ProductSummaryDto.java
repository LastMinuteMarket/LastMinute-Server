package com.lastminute.lastminuteserver.product.dto;

import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import com.lastminute.lastminuteserver.product.domain.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductSummaryDto(
        @NotNull Long productId,
        @NotNull PlacementDto placement,
        @NotNull ProductDetailDto detail,
        @NotNull Integer priceNow
) {

    public static ProductSummaryDto of(Product entity) {
        return ProductSummaryDto.builder()
                .productId(entity.getId())
                .placement(PlacementDto.of(entity.getPlacement()))
                .detail(ProductDetailDto.of(entity))
                .priceNow(entity.getPriceNow())
                .build();
    }
}
