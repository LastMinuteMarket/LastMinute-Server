package com.lastminute.lastminuteserver.payment.dto;

import com.lastminute.lastminuteserver.payment.domain.InstallmentPeriod;
import com.lastminute.lastminuteserver.payment.domain.Payment;
import com.lastminute.lastminuteserver.payment.domain.PaymentMethod;
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
    public Payment toEntity(){
        return Payment.builder()
                .pgId(this.pgId)
                .paymentMethod(PaymentMethod.findByKey(this.paymentMethod))
                .installmentPeriod(InstallmentPeriod.findByKey(this.installmentPeriod))
                .originalPrice(this.originalPrice)
                .fee(this.fee)
                .finalPrice(this.finalPrice)
                .build();
    }
}
