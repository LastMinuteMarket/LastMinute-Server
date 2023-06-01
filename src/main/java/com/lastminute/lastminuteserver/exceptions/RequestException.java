package com.lastminute.lastminuteserver.exceptions;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException {

    private final RequestExceptionCode exceptionCode;

    private RequestException(RequestExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public static RequestException of(RequestExceptionCode exceptionCode) {
        return new RequestException(exceptionCode);
    }
}
