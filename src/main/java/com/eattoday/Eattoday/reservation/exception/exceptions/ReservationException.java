package com.eattoday.Eattoday.reservation.exception.exceptions;

import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException{

    private final ReservationErrorCode reservationErrorCode;

    public ReservationException(ReservationErrorCode reservationErrorCode){
        super(reservationErrorCode.getErrorName() + reservationErrorCode.getErrorMessage());
        this.reservationErrorCode = reservationErrorCode;
    }

}
