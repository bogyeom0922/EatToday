package com.eattoday.Eattoday.user.exception.exceptions;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getCode() + ": " + errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
