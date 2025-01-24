package com.eattoday.Eattoday.user.controller.dto;

public record LoginRequest(
        String uid,
        String upassword
) {
}
