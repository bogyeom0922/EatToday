package com.eattoday.Eattoday.service;

import com.eattoday.Eattoday.dto.LikeDto;
import com.eattoday.Eattoday.entity.Like;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final LikeService likeService;
    private final StoreRepository storeRepository;

    public RecommendationService(final LikeService likeService, final StoreRepository storeRepository){
        this.likeService = likeService;
        this.storeRepository = storeRepository;
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
