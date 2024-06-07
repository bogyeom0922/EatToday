package com.eattoday.Eattoday.controller;

import com.eattoday.Eattoday.Service.ReviewService;
import com.eattoday.Eattoday.dto.ReviewDto;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
public class RestController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/category")
    public String category(Model model) {

        long count = storeRepository.count(); // store 개수 추출

        int random1 = (int) ((Math.random() * count));  // count까지의 숫자 중 랜덤 함수로 정수 추출
        Long id1 = (long) random1; // id와 같은 Long타입으로 변경
        Store store1 = storeRepository.findById(id1).orElse(null);
        model.addAttribute("randomStore1", store1); //model에 엔티티 값 저장

        int random2 = (int) ((Math.random() * count));  // count까지의 숫자 중 랜덤 함수로 정수 추출
        Long id2 = (long) random2; // id와 같은 Long타입으로 변경
        Store store2 = storeRepository.findById(id2).orElse(null);
        model.addAttribute("randomStore2", store2); //model에 엔티티 값 저장

        int random3 = (int) ((Math.random() * count));  // count까지의 숫자 중 랜덤 함수로 정수 추출
        Long id3 = (long) random3; // id와 같은 Long타입으로 변경
        Store store3 = storeRepository.findById(id3).orElse(null);
        model.addAttribute("randomStore3", store3); //model에 엔티티 값 저장

        return "rest/category";

    }

//    @GetMapping("/rest/{id}/{uid}") //매장 상세페이지
//    public String show(@PathVariable("id") Long id,@PathVariable String uid, Model model) {
//        log.info("id = " + id);
//        // 1. id를 조회해 데이터 가져오기
//        log.info("detail uid = "+uid);
//        Store storeentity = storeRepository.findById(id).orElse(null);
//        List<ReviewDto> reviewDtos = reviewService.reviews(id);
//        // 2. 모델에 데이터 등록하기
//        model.addAttribute("rest", storeentity);
//        model.addAttribute("reviewDtos", reviewDtos);
//
//        //user정보
//        User userEntity = userRepository.findByuid(uid).orElse(null);
//        model.addAttribute("user", userEntity);
//
//        // 3. 뷰 페이지 반환하기
//        return "rest/detail";
//    }

    @GetMapping("/rest/{id}/{uid}") //매장 상세페이지
    public String show(@PathVariable("id") Long id,@PathVariable String uid, Model model) {
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        log.info("detail uid = "+uid);
        Store storeentity = storeRepository.findById(id).orElse(null);
        List<ReviewDto> reviewDtos = reviewService.reviews(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("rest", storeentity);
        model.addAttribute("reviewDtos", reviewDtos);

        //user정보
        User userEntity = userRepository.findByuid(uid).orElse(null);
        model.addAttribute("user", userEntity);

        // 3. 뷰 페이지 반환하기
        return "rest/detail";
    }

}
