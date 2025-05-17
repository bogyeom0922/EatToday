package com.eattoday.Eattoday.reservation.exception.dto;

public record ReservationErrorResponse(
    String code,
    String message
)
{ }
