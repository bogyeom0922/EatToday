package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //특정 게시글 모든 리뷰 조회
    @Query(value = "SELECT * FROM Review WHERE store_id = :storeId", nativeQuery = true)
    List<Review> findByStoreId(Long storeId);
    List<Review> findByUserid(String userid);
}