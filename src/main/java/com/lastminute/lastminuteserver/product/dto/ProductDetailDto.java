package com.lastminute.lastminuteserver.product.dto;

import com.lastminute.lastminuteserver.common.EnumValid;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.product.domain.ReservationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;

@Builder
public record ProductDetailDto (

        @NotBlank(message = "예약된 상세 상품명을 입력해주세요.")
        @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
        String menu,

        @NotBlank(message = "상세 설명을 입력해주세요.")
        @Size(max = 1000, message = "내용은 최대 1000자까지 입력할 수 있습니다.")
        String description,

        @NotNull(message = "예약 인원수를 입력해주세요.")
        @Min(value = 0, message = "인원수는 음수가 될 수 없습니다.")
        Integer reservedPeoples,

        @NotNull(message = "예약 시간을 입력해주세요.")
        LocalDateTime reservedTime,

        @NotNull(message = "예약 타입을 입력해주세요.")
        @EnumValid(enumClass = ReservationType.class, message = "지원하지 않는 예약 타입 입니다.")
        ReservationType reservationType,

        @NotNull(message = "지불된 예약금을 입력해주세요.")
        @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
        Integer pricePaid,

        @NotNull(message = "시작 판매금액을 입력해주세요.")
        @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
        Integer priceNow
) {

    public static ProductDetailDto of(Product entity) {
        return ProductDetailDto.builder()
                .menu(entity.getMenu())
                .description(entity.getDescription())
                .reservedPeoples(entity.getReservedPeoples())
                .reservedTime(entity.getReservedTime())
                .reservationType(entity.getReservationType())
                .pricePaid(entity.getPricePaid())
                .priceNow(entity.getPriceNow())
                .build();
    }
}