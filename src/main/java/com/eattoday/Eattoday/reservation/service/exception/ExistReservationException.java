package com.eattoday.Eattoday.reservation.service.exception;

import com.eattoday.Eattoday.reservation.exception.exceptions.ReservationErrorCode;
import com.eattoday.Eattoday.reservation.exception.exceptions.ReservationException;

public class ExistReservationException extends ReservationException {

    public ExistReservationException(){
        super(ReservationErrorCode.NOT_EXIST_RESERVATION);
    }

}
