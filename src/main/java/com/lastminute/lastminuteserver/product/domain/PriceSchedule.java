package com.lastminute.lastminuteserver.product.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "price_schedule")
public class PriceSchedule {

    @EmbeddedId
    private PriceScheduleId scheduleId;

    @NotNull
    @Column
    @Min(value = 0, message = "가격은 0이상만 입력할 수 있습니다.")
    private Integer price;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    public PriceSchedule(PriceScheduleId scheduleId, Integer price) {
        this.scheduleId = scheduleId;
        this.price = price;
    }

    @Builder
    public PriceSchedule(Long productId, LocalDate applyAt, Integer price) {
        this(PriceScheduleId.builder()
                .productId(productId)
                .applyAt(applyAt)
                .build(), price);
    }

}
