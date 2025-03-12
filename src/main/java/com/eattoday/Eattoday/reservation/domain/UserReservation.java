package com.eattoday.Eattoday.reservation.domain;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
public class UserReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date reservationDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "User_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Reservation_id")
    private Reservation reservation;

    public UserReservation(User user, Reservation reservation, Date reservationDate) {
        this.user = user;
        this.reservation = reservation;
        this.reservationDate = reservationDate;
    }

}
