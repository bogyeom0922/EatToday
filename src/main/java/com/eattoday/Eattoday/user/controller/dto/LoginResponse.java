package com.eattoday.Eattoday.user.controller.dto;

public record LoginResponse(
        Long id,
        String uname,
        String uid,
        String upassword,
        String email
) {
}
