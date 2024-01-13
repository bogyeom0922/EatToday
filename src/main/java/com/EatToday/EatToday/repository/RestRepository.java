package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.Entity.Rest;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface RestRepository extends CrudRepository<Rest, Long>  {
    @Override
    ArrayList<Rest> findAll();
}

