package com.eattoday.Eattoday.user.mapper;

import com.eattoday.Eattoday.user.controller.dto.UserResponse;
import com.eattoday.Eattoday.user.domain.User;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUname(),
                user.getUid(),
                user.getUpassword(),
                user.getEmail());
    }
}
