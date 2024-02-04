package com.EatToday.EatToday.controller;

import com.EatToday.EatToday.Service.StoreService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value ="/storelist") //전체 식당 조회
    public String home(Model model,@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {
        Page<Store> storeList = storeService.storePage(pageable);

        model.addAttribute("list",storeList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        model.addAttribute("hasNext", storeList.hasNext());
        model.addAttribute("hasPrev", storeList.hasPrevious());
        return "storelist";
    }

    @GetMapping(value ="/storelist/filter") //검색 기능을 통한 식당 조회
    public String search(Model model, @RequestParam(value = "search-option") String option,
                         @RequestParam(value = "search")String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {
        Page<Store> storeList = null;

        if(option.equals("region")){
            storeList = storeService.search2(keyword, pageable);
            }
        else{
            storeList = storeService.search(keyword, pageable);
        }

        if(storeList == null)
            return "storelist";

        model.addAttribute("filterList",storeList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        model.addAttribute("hasNext", storeList.hasNext());
        model.addAttribute("hasPrev", storeList.hasPrevious());
        model.addAttribute("keyword", keyword);
        return "storelist-filter";
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
