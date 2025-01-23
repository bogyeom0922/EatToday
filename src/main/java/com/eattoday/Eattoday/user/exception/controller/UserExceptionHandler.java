package com.eattoday.Eattoday.user.exception.controller;

import com.eattoday.Eattoday.user.exception.controller.dto.UserErrorResponse;
import com.eattoday.Eattoday.user.exception.exceptions.UserErrorCode;
import com.eattoday.Eattoday.user.exception.exceptions.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserErrorResponse> handleException(UserException e) {
        UserErrorCode errorCode = e.getErrorCode();
        UserErrorResponse response = new UserErrorResponse(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
