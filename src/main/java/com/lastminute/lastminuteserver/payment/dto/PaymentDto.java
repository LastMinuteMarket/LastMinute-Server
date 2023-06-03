package com.lastminute.lastminuteserver.payment.dto;

import com.lastminute.lastminuteserver.common.EnumValid;
import com.lastminute.lastminuteserver.payment.domain.InstallmentPeriod;
import com.lastminute.lastminuteserver.payment.domain.Payment;
import com.lastminute.lastminuteserver.payment.domain.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentDto(
    @NotNull
    @EnumValid(enumClass = PaymentMethod.class, message = "지원하지 않는 결제 방식입니다.")
    PaymentMethod paymentMethod,

    @NotNull
    @EnumValid(enumClass = InstallmentPeriod.class, message = "지원하지 않는 기간입니다.")
    InstallmentPeriod installmentPeriod,

    Integer originalPrice,

    Integer fee,

    Integer finalPrice,

    LocalDateTime acceptedAt,

    Boolean cancelAvailable
) {
    public static PaymentDto of(Payment entity){
        return PaymentDto.builder()
                .paymentMethod(entity.getPaymentMethod())
                .installmentPeriod(entity.getInstallmentPeriod())
                .originalPrice(entity.getOriginalPrice())
                .fee(entity.getFee())
                .finalPrice(entity.getFinalPrice())
                .acceptedAt(entity.getAcceptedAt())
                .cancelAvailable(entity.getCancelAvailable())
                .build();
    }
}
