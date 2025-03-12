package com.eattoday.Eattoday.reservation.service;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.reservation.repository.ReservationRepository;
import com.eattoday.Eattoday.reservation.repository.UserReservationRepository;
import com.eattoday.Eattoday.reservation.service.exception.ExistReservationException;
import com.eattoday.Eattoday.store.entity.Store;
import com.eattoday.Eattoday.store.repository.StoreRepository;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserReservationService {

    private final UserReservationRepository userReservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public UserReservationService(UserReservationRepository userReservationRepository, UserRepository userRepository, StoreRepository storeRepository){
        this.userReservationRepository = userReservationRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    public UserReservation registerReservation(Long userId, Long storeId, LocalDateTime reservationDate){
        User user = getUser(userId);
        Store store = getStore(storeId);
        UserReservation userReservation = new UserReservation(user, store, reservationDate);
        userReservationRepository.save(userReservation);

        return userReservation;
    }

    public UserReservation deleteReservation(Long reservationId){
        UserReservation reservation = userReservationRepository.findById(reservationId)
                .orElseThrow(ExistReservationException::new);
        userReservationRepository.deleteByReservationId(reservationId);

        return reservation;
    }

    private Store getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 매장이 없습니다."));
        return store;
    }

    private User getUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 user가 없습니다."));
        return user;
    }

}
