package com.eattoday.Eattoday.reservation.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReservationErrorCode {

    NOT_EXIST_RESERVATION("notExistReservation", "해당하는 예약이 없습니다", HttpStatus.BAD_REQUEST);

    private String errorName;
    private String errorMessage;
    private HttpStatus status;

    ReservationErrorCode(String errorName, String errorMessage, HttpStatus status){
        this.errorName = errorName;
        this.errorMessage = errorMessage;
        this.status = status;
    }

}
