package com.lastminute.lastminuteserver.exceptions;

import lombok.Getter;

@Getter
public final class S3FileUploadException extends S3FileException {

    public S3FileUploadException(String message) {
        super(message);
    }

    public S3FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public S3FileUploadException(Throwable cause) {
        super(cause);
    }
}
