package com.lastminute.lastminuteserver.purchase.domain;

import com.lastminute.lastminuteserver.common.Convertable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseState implements Convertable {
    PAID("PAID"),
    COMPLETED("COMPLETED"),
    WITHDRAWN("WITHDRWAN");

    private final String key;

    public static PurchaseState findByKey(Object key){
        return Convertable.findByKey(key, PurchaseState.class);
    }
}
