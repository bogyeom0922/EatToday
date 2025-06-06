package com.eattoday.Eattoday.recommend.controller;

import com.eattoday.Eattoday.security.service.CustomUserDetailsService;
import com.eattoday.Eattoday.store.entity.Store;
import com.eattoday.Eattoday.recommend.service.RecommendationService;
import com.eattoday.Eattoday.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/recommend")
public class recommendApiController {

    private final RecommendationService recommendationService;

    public recommendApiController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/allRecommend")
    public ResponseEntity<List<Store>> findLikes(){
        User user = CustomUserDetailsService.getCurrentUserFromSecurityContext();
        String uid = user.getUid();

        List<String> myLikeCategory = recommendationService.findCategoryOfStore(uid);
        HashMap<String, Integer> bestCategory = recommendationService.pickBestCategory(myLikeCategory);
        HashMap<String, Integer> sortCategory = recommendationService.sortCategory(bestCategory);
        String pickedCategory = recommendationService.getBestCategory(sortCategory);
        List<Store> randomStore = recommendationService.getRecommendedStores(pickedCategory);

        return ResponseEntity.status(HttpStatus.OK).body(randomStore);
    }

}
