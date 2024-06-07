package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}