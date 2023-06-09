package com.lastminute.lastminuteserver.product.dto;

import com.lastminute.lastminuteserver.placement.dto.PlacementDto;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Builder
public record ProductAllDto(
        @NotNull Long productId,
        @NotNull UserProfileDto writer,
        @NotNull PlacementDto placement,
        @NotNull ProductDetailDto detail,
        @NotNull List<MultipartFile> images
) {

    public void putImage(MultipartFile file) {
        this.images.add(file);
    }

    public void putImages(Collection<MultipartFile> files) {
        this.images.addAll(files);
    }

    public static ProductAllDto of(Product entity) {
        return ProductAllDto.builder()
                .productId(entity.getId())
                .writer(UserProfileDto.of(entity.getWriter()))
                .placement(PlacementDto.of(entity.getPlacement()))
                .detail(ProductDetailDto.of(entity))
                .build();
    }
}