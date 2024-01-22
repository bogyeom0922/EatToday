package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Override
    ArrayList<Store> findAll();

    ArrayList<Store> findByCategory(String keyword);
}
