package com.EatToday.EatToday.controller;

import com.EatToday.EatToday.dto.ReviewDto;
import com.EatToday.EatToday.service.ReviewService;
import com.EatToday.EatToday.service.StoreService;
import com.EatToday.EatToday.dto.StoreDto;
import com.EatToday.EatToday.repository.StoreRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.EatToday.EatToday.entity.Store;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class RestController {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ReviewService reviewService;

    private StoreService storeService;

    public RestController(StoreService storeService){
        this.storeService = storeService;
    }

    @GetMapping(value ="/storelist")
    public String home(Model model) throws IOException {
        List<StoreDto> storeDtoList = storeService.getBoardList();
        model.addAttribute("list",storeDtoList);
        return "storelist";
    }
 
    @GetMapping("/rest/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        Store storeentity = storeRepository.findById(id).orElse(null);
        List<ReviewDto> reviewsDtos = reviewService.reviews(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("rest", storeentity);
        model.addAttribute("reviewDtos", reviewsDtos);
        // 3. 뷰 페이지 반환하기
        return "rest/detail";
    }

    @GetMapping("/category")
    public String category(Model model) {
        int randInt1 = (int)((Math.random()*329)+988);
        Long id1=Long.valueOf(randInt1);
        Store storeentity1 = storeRepository.findById(id1).orElse(null);
        model.addAttribute("randomStore1", storeentity1);

        int randInt2 = (int)((Math.random()*329)+988);
        Long id2=Long.valueOf(randInt2);
        Store storeentity2 = storeRepository.findById(id2).orElse(null);
        model.addAttribute("randomStore2", storeentity2);

        int randInt3 = (int)((Math.random()*329)+988);
        Long id3=Long.valueOf(randInt3);
        Store storeentity3 = storeRepository.findById(id3).orElse(null);
        model.addAttribute("randomStore3", storeentity3);
        return "category";
    }

}

