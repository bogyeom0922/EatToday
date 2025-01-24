package com.eattoday.Eattoday.user.mapper;

import com.eattoday.Eattoday.user.controller.dto.LoginResponse;
import com.eattoday.Eattoday.user.domain.User;

public class UserMapper {

    public static LoginResponse toLoginResponse(User user) {
        return new LoginResponse(
                user.getId(),
                user.getUname(),
                user.getUid(),
                user.getUpassword(),
                user.getEmail());
    }
}
