package com.lastminute.lastminuteserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RequestExceptionCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사용자가 없습니다."),
    USER_ALREADY_WITHDRAWN(HttpStatus.CONFLICT, "이미 탈퇴한 사용자입니다."),
    USER_ILLEGAL_BEHAVIOR(HttpStatus.FORBIDDEN, "허용되지 않은 행동입니다."),
    USER_NAME_NOT_ALLOWED(HttpStatus.UNPROCESSABLE_ENTITY, "사용할 수 없는 닉네임입니다."),
    USER_ILLEGAL_STATE(HttpStatus.UNPROCESSABLE_ENTITY, "상태 변경이 허용되지 않는 사용자입니다."),

    FORBIDDEN_NAME_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 금지 이름이 없습니다."),
    FORBIDDEN_NAME_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 등록된 금지 이름입니다."),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 상품이 없습니다."),
    PRODUCT_NOT_VISIBLE(HttpStatus.BAD_REQUEST, "상품 불러오기에 실패했습니다."),
    PRODUCT_ALREADY_HIDDEN(HttpStatus.CONFLICT, "이미 삭제된 게시글입니다."),
    PRODUCT_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 상품에 대해 권한이 없습니다."),

    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 결제 내역과 일치하는 정보가 없습니다"),
    PAYMENT_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "내부적으로 발생한 에러로 결제에 실패했습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
