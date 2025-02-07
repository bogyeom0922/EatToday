package com.eattoday.Eattoday.recommend.controller;

import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.recommend.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class recommendApiController {

    private final RecommendationService recommendationService;

    public recommendApiController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommend/{uid}")
    public ResponseEntity<List<Store>> findLikes(@PathVariable String uid){
        List<String> myLikeCategory = recommendationService.findCategoryOfStore(uid);
        HashMap<String, Integer> bestCategory = recommendationService.pickBestCategory(myLikeCategory);
        HashMap<String, Integer> sortCategory = recommendationService.sortCategory(bestCategory);
        String pickedCategory = recommendationService.getBestCategory(sortCategory);
        List<Store> randomStore = recommendationService.getRecommendedStores(pickedCategory);

        return ResponseEntity.status(HttpStatus.OK).body(randomStore);
    }

}
