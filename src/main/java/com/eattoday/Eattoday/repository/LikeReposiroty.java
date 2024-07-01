package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeReposiroty extends JpaRepository<Like,Long> {

}
