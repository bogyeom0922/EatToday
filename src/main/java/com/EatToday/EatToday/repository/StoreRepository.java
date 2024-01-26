package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Override
    ArrayList<Store> findAll();

    @Query(value = "select s from Store s where s.category = ?1")
    Page<Store> findByCategory(String category, Pageable pageable);
    @Query(value = "select m from Store m where m.store_address Like %?1%")
    Page<Store> findByStore_addressContaining(String address, Pageable pageable);

}
