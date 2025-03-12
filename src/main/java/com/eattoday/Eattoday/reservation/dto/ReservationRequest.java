package com.eattoday.Eattoday.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {

    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationTime;

}
