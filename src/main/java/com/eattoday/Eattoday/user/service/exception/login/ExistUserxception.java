package com.eattoday.Eattoday.user.service.exception.login;

import com.eattoday.Eattoday.user.exception.exceptions.UserErrorCode;
import com.eattoday.Eattoday.user.exception.exceptions.UserException;

public class ExistUserxception extends UserException {

    public ExistUserxception() {
        super(UserErrorCode.NOT_EXIST_USER);
    }
}
