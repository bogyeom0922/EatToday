package com.eattoday.Eattoday.like.repository;

import com.eattoday.Eattoday.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeReposiroty extends JpaRepository<Like, Long> {

    @Query(value = "select * from Likes where user_id = ?1 and store_id = ?2", nativeQuery = true)
    Like findByUser_idAndStore_id(Long user_id, Long store_id);

    @Query("select l from Likes l where l.user.id = ?1")
    List<Like> findLikes(Long userId);

}
