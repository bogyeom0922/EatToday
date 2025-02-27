package com.eattoday.Eattoday.user.repository;

import com.eattoday.Eattoday.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    //uid 중복 검사
    boolean existsByuid(String uid);

    //uname 중복 검사
    boolean existsByuname(String uname);

    //email 중복 검사
    boolean existsByEmail(String email);

    Optional<User> findByuid(String uid);
    Optional<User> findByemail(String email);

    @Query(value = "select u from User u where u.uid = ?1")
    User findByUserId(String userName);

}
