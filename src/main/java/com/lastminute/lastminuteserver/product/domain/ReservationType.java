package com.lastminute.lastminuteserver.product.domain;

import com.lastminute.lastminuteserver.common.Convertable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationType implements Convertable {

    PREPAID("PREPAID", true),
    DEPOSIT("DEPOSIT", false);

    private final String convertKey;
    private final boolean allPaid;

}
