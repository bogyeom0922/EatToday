package com.eattoday.Eattoday.reservation.service;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.reservation.repository.UserReservationRepository;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserReservationService {

    private final UserReservationRepository userReservationRepository;
    private final UserRepository userRepository;

    public UserReservationService(UserReservationRepository userReservationRepository, UserRepository userRepository){
        this.userReservationRepository = userReservationRepository;
        this.userRepository = userRepository;
    }

    public UserReservation registerReservation(Long userId, Date reservationDate){
        User user = getUser(userId);
        Reservation reservation = getReservation();
        UserReservation userReservation = new UserReservation(user, reservation, reservationDate);
        userReservationRepository.save(userReservation);

        return userReservation;
    }

    private Reservation getReservation() {
        return new Reservation();
    }

    private User getUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 user가 없습니다."));
        return user;
    }

}
