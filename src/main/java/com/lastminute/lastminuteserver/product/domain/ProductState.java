package com.lastminute.lastminuteserver.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductState {

    OPEN("OPEN", true, true),
    HIDDEN("HIDDEN", false, false),
    SOLD_OUT("SOLD_OUT", true, false);

    private final String value;
    private final boolean visible;
    private final boolean purchasable;
}
