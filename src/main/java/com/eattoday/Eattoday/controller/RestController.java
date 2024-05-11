package com.eattoday.Eattoday.controller;

import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.StoreRepository;
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

@Slf4j
@Controller
public class RestController {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreService storeService;
    @GetMapping("/rest/{id}") //매장 상세페이지
    public String show(@PathVariable("id") Long id, Model model) {
        log.info("id = " + id); //매장 id 제대로 가져왔는지 log 확인

        Store storeentity = storeRepository.findById(id).orElse(null); // id 조회해 데이터 가져오기
        model.addAttribute("rest", storeentity); // 모델에 데이터 등록

        return "rest/detail"; // 뷰 페이지 반환
    }

    @GetMapping(value ="/storelist") //전체 식당 조회
    public String home(@PathVariable String uid, Model model,@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {


        log.info("StoreList uid = "+uid);

        Page<Store> storeList = storeService.storePage(pageable);
        model.addAttribute("list",storeList);

        return "rest/storelist";
}
