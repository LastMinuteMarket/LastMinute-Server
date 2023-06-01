package com.lastminute.lastminuteserver.product.dto;

import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import lombok.Builder;

@Builder
public record ProductDto (
    Long productId,
    UserProfileDto writer,
    PlacementDto placement,
    ProductDetailDto detail
) {
    public static ProductDto of(Product entity) {
        ProductDetailDto detail = ProductDetailDto.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .price(entity.getPrice())
                .build();

        return ProductDto.builder()
                .productId(entity.getId())
                .writer(UserProfileDto.of(entity.getWriter()))
                .placement(PlacementDto.of(entity.getPlacement()))
                .detail(detail)
                .build();
    }
}