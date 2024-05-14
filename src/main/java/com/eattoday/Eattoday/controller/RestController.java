package com.eattoday.Eattoday.controller;

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
    public String home(@PathVariable String uid, Model model) throws IOException {


        log.info("StoreList uid = " + uid);

        List<Store> storeList = storeService.index();
        model.addAttribute("list", storeList);

        return "rest/storelist";
    }
}
