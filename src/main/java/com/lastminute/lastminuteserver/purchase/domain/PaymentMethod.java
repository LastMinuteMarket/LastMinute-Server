package com.lastminute.lastminuteserver.purchase.domain;

import com.lastminute.lastminuteserver.common.Convertable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod implements Convertable {
    CARD("CARD"),
    PHONE("PHONE");

    private final String key;

    public static PaymentMethod findByKey(Object key){
        return Convertable.findByKey(key, PaymentMethod.class);
    }
}
