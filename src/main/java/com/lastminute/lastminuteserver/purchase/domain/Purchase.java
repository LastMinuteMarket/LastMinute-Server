package com.lastminute.lastminuteserver.purchase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Purchase {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    InstallmentPeriod installmentPeriod;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    PurchaseState purchaseState = PurchaseState.PAID;

    @NotNull
    private Integer originalPrice;

    @NotNull
    private Integer fee = 10;

    @NotNull
    private Integer finalPrice;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime acceptedAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Purchase(PaymentMethod paymentMethod, InstallmentPeriod installmentPeriod,
                   Integer originalPrice, User user, Product product){
        this.paymentMethod = paymentMethod;
        this.installmentPeriod = installmentPeriod;
        this.originalPrice = originalPrice;
        this.user = user;
        this.product = product;
        this.finalPrice = (int)originalPrice * (fee / 100);
    }
}
