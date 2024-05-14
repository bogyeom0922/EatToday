package com.eattoday.Eattoday.service;

import com.eattoday.Eattoday.dto.StoreDto;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreRepository storeRepository;

    public List<Store> index(){
        return storeRepository.findAll();
    }

    @Transactional
    public List<StoreDto> getBoardList() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreDto> storeDtoList = new ArrayList<>();

        for (Store storeEntity : storeList) {
            StoreDto storeDto = StoreDto.builder()
                    .id(storeEntity.getId())
                    .category(storeEntity.getCategory())
                    .store_name(storeEntity.getStore_name())
                    .store_address(storeEntity.getStore_address())
                    .store_phone(storeEntity.getStore_phone())
                    .store_img(storeEntity.getStore_img())
                    .store_star(storeEntity.getStore_star())
                    .store_time(storeEntity.getStore_time())
                    .review_content(storeEntity.getReview_content())
                    .store_menu(storeEntity.getStore_menu())
                    .build();
            storeDtoList.add(storeDto);
        }
        return storeDtoList;
    }


}
