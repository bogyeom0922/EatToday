package com.eattoday.Eattoday.api;

import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class recommendApiController {

    private final RecommendationService recommendationService;
    private final StoreRepository storeRepository;

    public recommendApiController(RecommendationService recommendationService, StoreRepository storeRepository){
        this.recommendationService = recommendationService;
        this.storeRepository = storeRepository;
    }

    @GetMapping("recommend/{uid}")
    public ResponseEntity<List<Store>> findLikes(@PathVariable String uid){
        List<String> myLikeCategory = recommendationService.findCategoryOfStore(uid);
        HashMap<String, Integer> bestCategory = recommendationService.pickBestCategory(myLikeCategory);
        HashMap<String, Integer> sortCategory = recommendationService.sortCategory(bestCategory);
        String pickedCategory = recommendationService.getBestCategory(sortCategory);
        List<Store> randomStore = storeRepository.findStoreByCategory(pickedCategory);

        return ResponseEntity.status(HttpStatus.OK).body(randomStore);
    }

}
