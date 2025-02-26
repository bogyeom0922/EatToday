package com.eattoday.Eattoday.review.repository;

import com.eattoday.Eattoday.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //특정 게시글 모든 리뷰 조회
    @Query(value = "SELECT * FROM Review WHERE store_id = :storeId", nativeQuery = true)
    List<Review> findByStoreId(Long storeId);

    //특정 아이디 모든 리뷰 조회
    Page<Review> findByUserid(String userid, Pageable pageable);

}

