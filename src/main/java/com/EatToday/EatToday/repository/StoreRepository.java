package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
=======
>>>>>>> 0760822ca55994efda2a60d7ac310b129b67e3c9
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Override
    ArrayList<Store> findAll();

<<<<<<< HEAD
    ArrayList<Store> findByCategory(String keyword);
=======
>>>>>>> 0760822ca55994efda2a60d7ac310b129b67e3c9
}
