package com.lastminute.lastminuteserver.purchase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PurchaseResponseDto {
    private String paymentMethod;
    private Integer installmentPeriod;
    private String purchaseState;
    private Integer originalPrice;
    private String fee;
    private Integer finalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime acceptedAt;
    private String userNickName;
    private Long productId;

    public static PurchaseResponseDto from(Purchase purchase){
        return PurchaseResponseDto.builder()
                .paymentMethod(purchase.getPaymentMethod().getConvertKey())
                .installmentPeriod(purchase.getInstallmentPeriod().getConvertKey())
                .purchaseState(purchase.getPurchaseState().getConvertKey())
                .originalPrice(purchase.getOriginalPrice())
                .fee(""+purchase.getFee()+"%")
                .finalPrice(purchase.getFinalPrice())
                .acceptedAt(purchase.getAcceptedAt())
                .userNickName(purchase.getUser().getNickname())
                .productId(purchase.getProduct().getId())
                .build();
    }
}
