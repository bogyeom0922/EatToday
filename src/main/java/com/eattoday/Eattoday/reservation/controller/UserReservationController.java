package com.eattoday.Eattoday.reservation.controller;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.reservation.dto.ReservationRequest;
import com.eattoday.Eattoday.reservation.service.UserReservationService;
import com.eattoday.Eattoday.user.domain.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class UserReservationController {

    private final UserReservationService userReservationService;
    public UserReservationController(UserReservationService userReservationService){
        this.userReservationService = userReservationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserReservation> reserve(@RequestBody ReservationRequest request){
        UserReservation userReservation = userReservationService.registerReservation(request.getUserId(), request.getStoreId(), request.getReservationDate());
        return ResponseEntity.ok().body(userReservation);
    }

    @PostMapping("/delete")
    public ResponseEntity<UserReservation> delete(@RequestBody Long reservationId){
        UserReservation reservation = userReservationService.deleteReservation(reservationId);
        return ResponseEntity.ok().body(reservation);
    }

    @GetMapping("/{uid}/reservation")
    public String show(@PathVariable("uid") Long userId, Model model) {
        List<UserReservation> userReservationsList = userReservationService.showReservation(userId);
        model.addAttribute("reservation", userReservationsList);

        return "userinfo/info_reservation";
    }

}
