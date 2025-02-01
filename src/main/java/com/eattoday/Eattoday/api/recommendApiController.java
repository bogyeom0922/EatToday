package com.eattoday.Eattoday.api;

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

    public recommendApiController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("recommend/{uid}")
    public ResponseEntity<String> findLikes(@PathVariable String uid){
        List<String> myLikeCategory = recommendationService.findCategoryOfStore(uid);
        HashMap<String, Integer> bestCategory = recommendationService.pickBestCategory(myLikeCategory);
        HashMap<String, Integer> sortCategory = recommendationService.sortCategory(bestCategory);
        String pickedCategory = recommendationService.getBestCategory(sortCategory);

        return ResponseEntity.status(HttpStatus.OK).body(pickedCategory);
    }

}
