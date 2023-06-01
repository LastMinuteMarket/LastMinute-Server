package com.lastminute.lastminuteserver.payment.dto;

import com.lastminute.lastminuteserver.common.EnumValid;
import com.lastminute.lastminuteserver.payment.domain.InstallmentPeriod;
import com.lastminute.lastminuteserver.payment.domain.PaymentMethod;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentCreateDto(

    @NotNull(message = "결제 방식을 입력해주세요.")
    @EnumValid(enumClass = PaymentMethod.class, message = "지원하지 않는 결제 방식 입니다.")
    String paymentMethod,

    @NotNull(message = "할부 기간을 입력해주세요.")
    @EnumValid(enumClass = InstallmentPeriod.class, message = "지원하지 않는 할부 기간 입니다.")
    Integer installmentPeriod,

    @NotNull(message = "가격을 입력해주세요.")
    @Min(0) @Max(1000000)
    Integer originalPrice
) {

}
