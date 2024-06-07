package com.eattoday.Eattoday.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Review")
@Getter
@Setter
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
    @Column
    private String storeName;
    @Column
    private String userid;
    @Column
    private String body;

}
