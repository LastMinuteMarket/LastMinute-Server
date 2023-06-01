package com.lastminute.lastminuteserver.payment.dto;

import com.lastminute.lastminuteserver.payment.domain.Payment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentDto(
    String paymentMethod,
    Integer installmentPeriod,
    Integer originalPrice,
    Integer fee,
    Integer finalPrice,
    LocalDateTime acceptedAt,
    Boolean cancelAvailable
) {
    public static PaymentDto of(Payment entity){
        return PaymentDto.builder()
                .paymentMethod(entity.getPaymentMethod().getKey())
                .installmentPeriod(entity.getInstallmentPeriod().getKey())
                .originalPrice(entity.getOriginalPrice())
                .fee(entity.getFee())
                .finalPrice(entity.getFinalPrice())
                .acceptedAt(entity.getAcceptedAt())
                .cancelAvailable(entity.getCancelAvailable())
                .build();
    }
}
