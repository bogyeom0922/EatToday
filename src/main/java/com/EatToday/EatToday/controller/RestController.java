package com.EatToday.EatToday.controller;

import com.EatToday.EatToday.entity.Rest;
import com.EatToday.EatToday.repository.RestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Slf4j
@Controller
public class RestController {
    @Autowired
    private RestRepository restRepository;

    @GetMapping("/")
    public String title() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
 
    @GetMapping("/rest/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        Rest restEntity = restRepository.findById(id).orElse(null);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("rest", restEntity);
        // 3. 뷰 페이지 반환하기
        return "rest/detail";
    }
}
