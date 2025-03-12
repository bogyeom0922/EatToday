package com.eattoday.Eattoday.reservation.repository;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.reservation.domain.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReservationRepository extends JpaRepository<UserReservation, Long> {

    @Query("delete from UserReservation r where r.id = ?1")
    void deleteByReservationId(Long reservationId);

}
