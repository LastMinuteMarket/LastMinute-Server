package com.lastminute.lastminuteserver.exceptions;

import lombok.Getter;

@Getter
public class S3FileException extends InternalException {

    public S3FileException(String message) {
        super(message);
    }

    public S3FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public S3FileException(Throwable cause) {
        super(cause);
    }
}
