package com.eattoday.Eattoday.reservation.exception;

import com.eattoday.Eattoday.reservation.exception.dto.ReservationErrorResponse;
import com.eattoday.Eattoday.reservation.exception.exceptions.ReservationErrorCode;
import com.eattoday.Eattoday.reservation.exception.exceptions.ReservationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ReservationErrorResponse> handleException(ReservationException e){
        ReservationErrorCode reservationErrorCode = e.getReservationErrorCode();
        ReservationErrorResponse errorResponse = new ReservationErrorResponse(reservationErrorCode.getErrorName(), reservationErrorCode.getErrorMessage());
        return ResponseEntity.status(reservationErrorCode.getStatus()).body(errorResponse);
    }

}
