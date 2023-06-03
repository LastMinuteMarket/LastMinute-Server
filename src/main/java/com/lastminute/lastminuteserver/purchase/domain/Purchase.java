package com.lastminute.lastminuteserver.purchase.domain;

import com.lastminute.lastminuteserver.product.domain.Product;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Purchase {
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
    PurchaseState purchaseState;

    @NotNull
    private Integer originalPrice;

    @NotNull
    private Integer fee;

    @NotNull
    private Integer finalPrice;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime acceptedAt;

    @NotNull
    private Boolean cancelAvailable = true;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public void setCancelUnAvailable(){
        this.cancelAvailable = false;
    }

    @Builder
    public Purchase(PaymentMethod paymentMethod, InstallmentPeriod installmentPeriod, PurchaseState purchaseState,
                   Integer originalPrice, Integer fee, Integer finalPrice){
        this.paymentMethod = paymentMethod;
        this.installmentPeriod = installmentPeriod;
        this.purchaseState = purchaseState;
        this.originalPrice = originalPrice;
        this.fee = fee;
        this.finalPrice = finalPrice;
    }
}
