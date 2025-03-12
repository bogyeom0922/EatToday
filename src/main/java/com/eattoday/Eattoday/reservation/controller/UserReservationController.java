package com.eattoday.Eattoday.reservation.controller;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.reservation.service.UserReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/reservation")
public class UserReservationController {

    private final UserReservationService userReservationService;
    public UserReservationController(UserReservationService userReservationService){
        this.userReservationService = userReservationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserReservation> reserve(@RequestParam Long userId, @RequestParam Date reservationDate){
        UserReservation userReservation = userReservationService.registerReservation(userId, reservationDate);
        return ResponseEntity.ok().body(userReservation);
    }

    @PostMapping("/delete")
    public ResponseEntity<Reservation> delete(@RequestParam Long reservationId){
        Reservation reservation = userReservationService.deleteReservation(reservationId);
        return ResponseEntity.ok().body(reservation);
    }

}
