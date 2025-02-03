package com.eattoday.Eattoday.recommend.service.exception;

import com.eattoday.Eattoday.recommend.exception.exceptions.RecommendErrorCode;
import com.eattoday.Eattoday.recommend.exception.exceptions.RecommendException;

public class ExistRecommendException extends RecommendException {

    public ExistRecommendException(){
        super(RecommendErrorCode.NOT_EXIST_LIKE);
    }

}
