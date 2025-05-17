package com.eattoday.Eattoday.recommend.exception;

import com.eattoday.Eattoday.recommend.exception.dto.RecommendErrorResponse;
import com.eattoday.Eattoday.recommend.exception.exceptions.RecommendErrorCode;
import com.eattoday.Eattoday.recommend.exception.exceptions.RecommendException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecommendExceptionHandler {

    @ExceptionHandler(RecommendException.class)
    public ResponseEntity<RecommendErrorResponse> handleException(RecommendException e) {
        RecommendErrorCode errorCode = e.getRecommendErrorCode();
        RecommendErrorResponse response = new RecommendErrorResponse(errorCode.getErrorCode(), errorCode.getErrorMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}
