package com.eattoday.Eattoday.reservation.service.exception;

import com.eattoday.Eattoday.reservation.exception.exceptions.ReservationErrorCode;
import com.eattoday.Eattoday.reservation.exception.exceptions.ReservationException;

public class NotExistReservationException extends ReservationException {

    public NotExistReservationException(){
        super(ReservationErrorCode.NOT_EXIST_RESERVATION);
    }

}
