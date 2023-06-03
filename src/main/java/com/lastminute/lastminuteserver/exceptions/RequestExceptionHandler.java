package com.lastminute.lastminuteserver.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Object> handleException(RequestException requestException){
        RequestExceptionCode exceptionCode = requestException.getExceptionCode();
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(exceptionCode.getMessage());
    }
}
