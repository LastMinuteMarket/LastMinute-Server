package com.lastminute.lastminuteserver.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PriceScheduleId  implements Serializable {

    @Serial
    private static final long serialVersionUID = 421523209L;

    @NotNull
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @NotNull
    @Column(name = "apply_at", updatable = false)
    private LocalDate applyAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceScheduleId that = (PriceScheduleId) o;
        return Objects.equals(productId, that.productId) && Objects.equals(applyAt, that.applyAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, applyAt);
    }

    @Builder
    public PriceScheduleId(Long productId, LocalDate applyAt) {
        this.productId = productId;
        this.applyAt = applyAt;
    }
}
