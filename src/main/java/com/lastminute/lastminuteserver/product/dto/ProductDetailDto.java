package com.lastminute.lastminuteserver.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record ProductDetailDto (

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
    String title,

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 1_000, message = "내용은 최대 1000자까지 입력할 수 있습니다.")
    String content,

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    Integer price,

    List<MultipartFile> images
) {

    @Builder
    public ProductDetailDto(String title, String content, Integer price) {
        this(title, content, price, new ArrayList<>());
    }

    public void putImage(MultipartFile file) {
        this.images.add(file);
    }

    public void putImages(Collection<MultipartFile> files) {
        this.images.addAll(files);
    }
}