package com.eattoday.Eattoday.recommend.service;

import com.eattoday.Eattoday.dto.LikeDto;
import com.eattoday.Eattoday.entity.Like;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.recommend.service.exception.ExistRecommendException;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.service.LikeService;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationService {

    private final LikeService likeService;

    public RecommendationService(final LikeService likeService){
        this.likeService = likeService;
    }

    public HashMap<String, Integer> pickBestCategory(List<String> category){
        HashMap<String, Integer> pickedCategory = new HashMap<>();
        category.forEach(
                categoryData -> {
                    int count = pickedCategory.getOrDefault(categoryData, 0);
                    pickedCategory.put(categoryData, count);
                }
        );

        return pickedCategory;
    }

    public HashMap<String, Integer> sortCategory(HashMap<String, Integer> category){
        return category.entrySet()
                .stream()
                .sorted((category1, category2) -> category2.getValue().compareTo(category1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (c1, c2) -> c1,
                        LinkedHashMap::new
                ));
    }

    public String getBestCategory(HashMap<String, Integer> category){
        Iterator<String> categorys = category.keySet().iterator();
        if(!categorys.hasNext()){
            throw new ExistRecommendException();
        }
        return categorys.next();
    }

    public List<String> findCategoryOfStore(String uid){
        List<Like> myLike = likesOfUser(uid);

        return myLike.stream()
                .map(this::getCategory)
                .collect(Collectors.toList());
    }

    private List<Like> likesOfUser(String uid){
        return likeService.myLikeEntity(uid);
    }

    private String getCategory(Like like){
        Store store = like.getStore();
        String category = store.getCategory();

        return category;
    }

}
