package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //특정 게시글 모든 리뷰 조회
    @Query(value = "SELECT * FROM review WHERE store_id = :storeId",
            nativeQuery = true)
    List<Review> findByStoreId(Long storeId);
    //특정 닉네임 모든 댓글 조회
    @Query(value = "SELECT * FROM review WHERE user_id = :userId",
            nativeQuery = true)
    List<Review> findByUserId(Long userId);
}
