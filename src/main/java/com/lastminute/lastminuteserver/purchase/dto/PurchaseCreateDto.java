package com.lastminute.lastminuteserver.purchase.dto;

import com.lastminute.lastminuteserver.common.EnumValid;
import com.lastminute.lastminuteserver.purchase.domain.InstallmentPeriod;
import com.lastminute.lastminuteserver.purchase.domain.PaymentMethod;
import com.lastminute.lastminuteserver.purchase.domain.Purchase;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseCreateDto {
    @NotNull(message = "결제 방식을 입력해주세요.")
    @EnumValid(enumClass = PaymentMethod.class, message = "지원하지 않는 결제 방식 입니다.")
    private String paymentMethod;

    @NotNull(message = "할부 기간을 입력해주세요.")
    @EnumValid(enumClass = InstallmentPeriod.class, message = "지원하지 않는 할부 기간 입니다.")
    private Integer installmentPeriod;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(0) @Max(1000000)
    private Integer originalPrice;

    public Purchase toEntity(){
        return Purchase.builder()
                .paymentMethod(PaymentMethod.findByKey(paymentMethod))
                .installmentPeriod(InstallmentPeriod.findByKey(installmentPeriod))
                .originalPrice(originalPrice)
                .build();
    }
}
