package com.EatToday.EatToday.controller;

import com.EatToday.EatToday.service.StoreService;
import com.EatToday.EatToday.dto.StoreDto;
import com.EatToday.EatToday.repository.StoreRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        // 2. 모델에 데이터 등록하기
        model.addAttribute("rest", storeentity);
        // 3. 뷰 페이지 반환하기
        return "rest/detail";
    }
}
