package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //uid 중복 검사
    boolean existsByuid(String uid);
    //uname 중복 검사
    boolean existsByuname(String uname);
    //email 중복 검사
    boolean existsByEmail(String email);
}
