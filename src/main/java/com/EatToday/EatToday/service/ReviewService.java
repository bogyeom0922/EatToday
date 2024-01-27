package com.EatToday.EatToday.service;

import com.EatToday.EatToday.repository.ReviewRepository;
import com.EatToday.EatToday.repository.StoreRepository;
import com.EatToday.EatToday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;
}
