package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByuid(String uid);
}
