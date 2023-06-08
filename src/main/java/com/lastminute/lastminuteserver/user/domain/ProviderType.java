package com.lastminute.lastminuteserver.user.domain;

import com.lastminute.lastminuteserver.common.Convertable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderType implements Convertable {

    DIRECT("DIRECT"),
    KAKAO("KAKAO");

    private final String convertKey;
}
