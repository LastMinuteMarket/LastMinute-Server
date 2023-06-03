package com.lastminute.lastminuteserver.common;

import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;

public record ResponseDto<T> (
    boolean success,
    T data,
    String message,
    String errorCode
) {

    public static ResponseDto<Void> empty() {
        return new ResponseDto<>(false, null, null, null);
    }

    public static ResponseDto<Void> of(RequestExceptionCode code) {
        return new ResponseDto<>(false, null, code.getMessage(), code.name());
    }

    public static <T> ResponseDto<T> of(T data) {
        return new ResponseDto<>(true, data, null, null);
    }
}
