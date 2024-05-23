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

}
