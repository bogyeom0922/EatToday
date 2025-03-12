package com.eattoday.Eattoday.reservation.controller;

import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.reservation.service.UserReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final UserReservationService userReservationService;
    public ReservationController(UserReservationService userReservationService){
        this.userReservationService = userReservationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserReservation> reserve(Long userId, Date reservationDate){
        UserReservation userReservation = userReservationService.registerReservation(userId, reservationDate);
        return ResponseEntity.ok().body(userReservation);
    }

}
