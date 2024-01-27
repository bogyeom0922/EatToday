package com.EatToday.EatToday.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor //생성자 대체 lombok
@ToString //toString()대체 lombok
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column
    private String body;
}
