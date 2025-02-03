package com.eattoday.Eattoday.recommend.exception.exceptions;

import com.eattoday.Eattoday.recommend.exception.dto.RecommendErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RecommendErrorCode {

    NOT_EXIST_LIKE("notExistLike", "즐겨찾기 목록이 비어있습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    RecommendErrorCode(String errorCode, String errorMessage, HttpStatus status){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

}
