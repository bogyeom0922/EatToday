package com.EatToday.EatToday.service;

import com.EatToday.EatToday.dto.StoreDto;
import com.EatToday.EatToday.entity.Store;
import com.EatToday.EatToday.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class StoreService{
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
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

    @Transactional
    public Page<Store> storePage(Pageable pageable){
        Page<Store> storePage = storeRepository.findAll(pageable);
        return storePage;
    }
    @Transactional
    public Page<Store> filterByCategory(String category, Pageable pageable){
        Page<Store> search = storeRepository.findByCategory(category, pageable);
        return search;
    }

    @Transactional
    public Page<Store> filterByStore_address(String address, Pageable pageable){
        Page<Store> search2 = storeRepository.findByStore_addressContaining(address, pageable);

        return search2;
    }

    @Transactional
    public Page<Store> allSearch(String keyword, Pageable pageable){
        Page<Store> allSearch = storeRepository.findByAllContent(keyword, pageable);

        return allSearch;
    }


}
