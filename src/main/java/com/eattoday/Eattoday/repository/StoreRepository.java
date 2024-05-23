package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    @Override
    ArrayList<Store> findAll();

    @Query(value = "select p from Store p where p.store_img Like %?1% OR p.store_name Like %?1% OR p.store_time Like %?1% OR p.store_phone Like %?1% OR p.store_star Like %?1%")
    List<Store> findByAllContent(String keyword);

}
