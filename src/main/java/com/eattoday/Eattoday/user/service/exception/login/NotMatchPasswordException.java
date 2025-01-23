package com.eattoday.Eattoday.user.service.exception.login;

import com.eattoday.Eattoday.user.exception.exceptions.UserErrorCode;
import com.eattoday.Eattoday.user.exception.exceptions.UserException;

public class NotMatchPasswordException extends UserException {

    public NotMatchPasswordException() {
        super(UserErrorCode.NOT_MATCH_PASSWORD);
    }
}
