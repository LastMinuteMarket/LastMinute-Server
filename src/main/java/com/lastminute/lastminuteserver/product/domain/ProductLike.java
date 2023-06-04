package com.lastminute.lastminuteserver.product.domain;

import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_like")
public class ProductLike {

    @EmbeddedId
    private ProductLikeId productLikeId;

    @NotNull
    @Column(name = "liked_at", updatable = false)
    @CreatedDate
    private LocalDateTime likedAt;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    public ProductLike(ProductLikeId productLikeId) {
        this.productLikeId = productLikeId;
    }

    @Builder
    public ProductLike(Long userId, Long productId) {
        this(ProductLikeId.builder()
                .userId(userId)
                .productId(productId)
                .build());
    }
}
