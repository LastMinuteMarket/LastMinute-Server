package com.lastminute.lastminuteserver.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ProductLikeId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1512232361234L;

    @NotNull
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @NotNull
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductLikeId that = (ProductLikeId) o;
        return Objects.equals(productId, that.productId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId);
    }

    @Builder
    public ProductLikeId(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }
}
