package com.eattoday.Eattoday.user.domain;

import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.user.service.exception.login.NotMatchPasswordException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor //생성자 대체 lombok
@ToString //toString()대체 lombok
@NoArgsConstructor //기본 생성자 대체 lombok
@Entity(name = "User") // Entity는 자바 객체가 DB를 이해할 수 있도록 만든 것
public class User {

    @Id // 엔티티 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column //uid 필드 선언
    private String uid;
    @Column //uname 필드 선언
    private String uname;
    @Column //upassword 필드 선언
    private String upassword;
    @Column //email 필드 선언
    private String email;
    @Column
    private String role;
    @OneToMany(mappedBy = "user")
    private List<UserReservation> userReservations = new ArrayList<>();

    public void checkPassword(String password) {
        if(!Objects.equals(upassword, password)) {
            throw new NotMatchPasswordException();
        }
    }
}
