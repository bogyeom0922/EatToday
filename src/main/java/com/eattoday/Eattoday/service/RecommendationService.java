package com.eattoday.Eattoday.service;

import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final LikeService likeService;

    public RecommendationService(final LikeService likeService){
        this.likeService = likeService;
    }

}
