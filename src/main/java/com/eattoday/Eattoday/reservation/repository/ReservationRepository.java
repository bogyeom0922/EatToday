package com.eattoday.Eattoday.reservation.repository;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
