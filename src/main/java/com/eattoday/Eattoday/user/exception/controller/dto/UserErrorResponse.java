package com.eattoday.Eattoday.user.exception.controller.dto;

public record UserErrorResponse(
        String code,
        String message
) {
}
