package com.lastminute.lastminuteserver.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;
}
