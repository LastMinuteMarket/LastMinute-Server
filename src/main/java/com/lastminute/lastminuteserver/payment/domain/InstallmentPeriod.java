package com.lastminute.lastminuteserver.payment.domain;

import com.lastminute.lastminuteserver.common.Convertable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstallmentPeriod implements Convertable {
    ZERO(0),
    A_MONTH(1),
    QUARTER(3),
    HALF_YEAR(6),
    A_YEAR(12),
    TWO_YEAR(24);

    private final Integer convertKey;

    public static InstallmentPeriod findByKey(Object key){
        return Convertable.findByKey(key, InstallmentPeriod.class);
    }
}
