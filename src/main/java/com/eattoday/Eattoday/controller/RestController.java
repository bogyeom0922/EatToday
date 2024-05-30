package com.eattoday.Eattoday.controller;

import com.eattoday.Eattoday.dto.StoreDto;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.service.StoreService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class RestController {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreService storeService;

    @GetMapping(value ="/storelist") //전체 식당 조회
    public String home(@PathVariable String uid, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) throws IOException {


        log.info("StoreList uid = " + uid);

        Page<Store> storeList = storeService.storePage(pageable);
        model.addAttribute("list", storeList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        model.addAttribute("hasNext", storeList.hasNext());
        model.addAttribute("hasPrev", storeList.hasPrevious());
        return "rest/storelist";
    }

    @GetMapping(value = "/storelist/search/{uid}")
    public String search(@PathVariable String uid, Model model, @RequestParam(value = "search")String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        log.info("search uid = " + uid);
        Page<Store> storeList = null;

        storeList = storeService.allSearch(keyword, pageable);

        if(storeList == null)
            return "rest/storelist";

        model.addAttribute("searchList", storeList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next",pageable.next().getPageNumber());
        model.addAttribute("hasNext", storeList.hasNext());
        model.addAttribute("hasPrev", storeList.hasPrevious());
        model.addAttribute("keyword", keyword);
        return "rest/storelist-search";
    }

    @GetMapping(value = "/storelist/filter/{uid}/{filter}")
    public String filter(@PathVariable("filter") String filter, @PathVariable String uid, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException{
        String[] keyword = filter.split("-");
        String filter_name = keyword[1];

        Page<Store> FilteredList = null;
        if(filter.contains("category")){
            FilteredList = storeService.filterByCategory(filter_name, pageable);
        }
        log.info("filter uid = " + uid);
        log.info(filter_name);
        log.info(filter);

        model.addAttribute("filterList", FilteredList);

        return "rest/storelist-filter";
    }



}
