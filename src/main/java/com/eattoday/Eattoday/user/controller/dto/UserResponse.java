package com.eattoday.Eattoday.user.controller.dto;

public record UserResponse(
        Long id,
        String uname,
        String uid,
        String upassword,
        String email
) {
}
