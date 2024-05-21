package com.eattoday.Eattoday.controller;

import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class RestController {
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/rest/{id}") //매장 상세페이지
    public String show(@PathVariable("id") Long id, Model model) {
        log.info("id = " + id); //매장 id 제대로 가져왔는지 log 확인

        Store storeentity = storeRepository.findById(id).orElse(null); // id 조회해 데이터 가져오기
        model.addAttribute("rest", storeentity); // 모델에 데이터 등록

        return "rest/detail"; // 뷰 페이지 반환
    }

    @GetMapping("/category")
    public String category(Model model)
    {
        long count = storeRepository.count(); // store 개수 추출

        int random1 = (int)((Math.random()*count));  // count까지의 숫자 중 랜덤 함수로 정수 추출
        Long id1=Long.valueOf(random1); // id와 같은 Long타입으로 변경
        Store store1 = storeRepository.findById(id1).orElse(null);
        model.addAttribute("randomStore1", store1); //model에 엔티티 값 저장

        int random2 = (int)((Math.random()*count));  // count까지의 숫자 중 랜덤 함수로 정수 추출
        Long id2=Long.valueOf(random2); // id와 같은 Long타입으로 변경
        Store store2 = storeRepository.findById(id2).orElse(null);
        model.addAttribute("randomStore2", store2); //model에 엔티티 값 저장

        int random3 = (int)((Math.random()*count));  // count까지의 숫자 중 랜덤 함수로 정수 추출
        Long id3=Long.valueOf(random3); // id와 같은 Long타입으로 변경
        Store store3 = storeRepository.findById(id3).orElse(null);
        model.addAttribute("randomStore3", store3); //model에 엔티티 값 저장

        return "rest/category";
    }

}
