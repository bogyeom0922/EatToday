package com.eattoday.Eattoday.recommend.exception.exceptions;

import lombok.Getter;

@Getter
public class RecommendException extends RuntimeException{

    private final RecommendErrorCode recommendErrorCode;

    public RecommendException(RecommendErrorCode recommendErrorCode){
        super(recommendErrorCode.getErrorCode() + ": " + recommendErrorCode.getErrorMessage());
        this.recommendErrorCode = recommendErrorCode;
    }

}
