package com.eattoday.Eattoday.store.repository;

import com.eattoday.Eattoday.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    ArrayList<Store> findAll();

    @Query(value = "select * from Store where store_img Like %?1% OR store_name Like %?1% OR store_time Like %?1% OR store_phone Like %?1% OR store_star Like %?1%", nativeQuery = true)
    Page<Store> findByAllContent(String keyword, Pageable pageable);

    @Query(value = "select s from Store s where s.category = ?1")
    Page<Store> findByCategory(String category, Pageable pageable);

    @Query(value = "select m from Store m where m.store_address LIKE %?1%")
    Page<Store> findByStore_addressContaining(String address, Pageable pageable);

    @Query(value = "select s from Store s where s.category = ?1 order by rand() limit 3")
    List<Store> findStoreByCategory(String category);

    @Query(value = "select * from store order by rand() limit 3", nativeQuery = true)
    List<Store> findRandomStores();

}
