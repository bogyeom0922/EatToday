package com.eattoday.Eattoday.reservation.domain;

import com.eattoday.Eattoday.reservation.domain.Reservation;
import com.eattoday.Eattoday.store.entity.Store;
import com.eattoday.Eattoday.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
public class UserReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "store_id")
    private Store store;

    public UserReservation(User user, Store store, LocalDateTime reservationDate) {
        this.user = user;
        this.store = store;
        this.reservationDate = reservationDate;
    }

}
