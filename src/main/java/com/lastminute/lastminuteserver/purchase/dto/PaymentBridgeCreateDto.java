package com.lastminute.lastminuteserver.purchase.dto;

import com.lastminute.lastminuteserver.purchase.domain.InstallmentPeriod;
import com.lastminute.lastminuteserver.purchase.domain.Payment;
import com.lastminute.lastminuteserver.purchase.domain.PaymentMethod;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import lombok.Builder;

@Builder
public record PaymentBridgeCreateDto(
    Long pgId,
    String paymentMethod,
    Integer installmentPeriod,
    Integer originalPrice,
    Integer fee,
    Integer finalPrice
) {
    public Purchase toEntity(){
        return Purchase.builder()
                .paymentMethod(PaymentMethod.findByKey(this.paymentMethod))
                .installmentPeriod(InstallmentPeriod.findByKey(this.installmentPeriod))
                .originalPrice(this.originalPrice)
                .fee(this.fee)
                .finalPrice(this.finalPrice)
                .build();
    }
}
