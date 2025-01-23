package com.eattoday.Eattoday.user.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {

    NOT_EXIST_USER("유저가 존재하지 않습니다.", "A001", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String code;
    private final HttpStatus httpStatus;

    UserErrorCode(String message, String code, HttpStatus httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
