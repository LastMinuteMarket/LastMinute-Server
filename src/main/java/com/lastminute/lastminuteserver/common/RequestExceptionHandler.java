package com.lastminute.lastminuteserver.common;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<ResponseDto<Void>> handleRequestException(RequestException e) {
        log.info("handled request exception", e);
        return ResponseEntity
                .status(e.getExceptionCode().getHttpStatus())
                .body(ResponseDto.of(e.getExceptionCode()));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ResponseDto<Void>> handleInternalException(RuntimeException e) {
        log.error("occur (unexpected) internal exception", e);
        return ResponseEntity
                .internalServerError()
                .body(ResponseDto.emptyFail());
    }
}
