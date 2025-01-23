package com.eattoday.Eattoday.user.controller.dto;

public record UserRequest(
        String uid,
        String upassword,
        String uname,
        String email
) {
}
