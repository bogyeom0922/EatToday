package com.eattoday.Eattoday.repository;

import com.eattoday.Eattoday.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    ArrayList<Store> findAll();
}
