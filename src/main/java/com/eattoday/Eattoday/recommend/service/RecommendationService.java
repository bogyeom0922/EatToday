package com.eattoday.Eattoday.recommend.service;

import com.eattoday.Eattoday.entity.Like;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.recommend.service.exception.ExistRecommendException;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.service.LikeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
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
                    pickedCategory.put(categoryData, count+1);
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

        return categorys.next();
    }

    public List<String> findCategoryOfStore(String uid){
        List<Like> myLike = likesOfUser(uid);

        return myLike.stream()
                .map(this::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Store> getRecommendedStores(String category) {
        if (category == null) {
            return getRandomStore();
        }
        List<Store> stores = storeRepository.findStoreByCategory(category);
        recommendException(stores);

        return stores;
    }

    private void recommendException(List<Store> stores){
        if(stores.isEmpty()){
            throw new ExistRecommendException();
        }
    }


    private List<Store> getRandomStore(){
        return storeRepository.findRandomStores();
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
