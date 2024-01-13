package com.EatToday.EatToday.mapper;

import com.EatToday.EatToday.DTO.StoreDto;
import com.EatToday.EatToday.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

}
