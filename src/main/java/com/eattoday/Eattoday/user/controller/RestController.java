package com.eattoday.Eattoday.user.controller;

import com.eattoday.Eattoday.security.service.CustomUserDetailsService;
import com.eattoday.Eattoday.review.service.ReviewService;
import com.eattoday.Eattoday.review.dto.ReviewDto;
import com.eattoday.Eattoday.store.entity.Store;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.store.repository.StoreRepository;
import com.eattoday.Eattoday.store.service.StoreService;
import com.eattoday.Eattoday.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    //storelist

    @GetMapping("/category")
    public String categoryPage(Model model){
        User currentUser = customUserDetailsService.getCurrentUserFromSecurityContext();
        String uid = currentUser.getUid();
        model.addAttribute("uid", uid);

        return "rest/category";
    }
    @GetMapping(value = "/storelist/{uid}") //전체 식당 조회
    public String storelist(@PathVariable String uid, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        log.info("StoreList uid = " + uid);

        Page<Store> storeList = storeService.storePage(pageable);
        model.addAttribute("list", storeList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", storeList.hasNext());
        model.addAttribute("hasPrev", storeList.hasPrevious());
        return "rest/storelist";

    }

    @GetMapping(value = "/storelist/search/{uid}")
    public String search(@PathVariable String uid, Model model, @RequestParam(value = "search") String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("search uid = " + uid);
        Page<Store> storeList = null;

        storeList = storeService.allSearch(keyword, pageable);

        if (storeList == null)
            return "rest/storelist";

        model.addAttribute("searchList", storeList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", storeList.hasNext());
        model.addAttribute("hasPrev", storeList.hasPrevious());
        model.addAttribute("keyword", keyword);
        return "rest/storelist-search";

    }

    @GetMapping(value = "/storelist/filter/{uid}/{filter}")
    public String filter(@PathVariable("filter") String filter, @PathVariable String uid, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        String[] keyword = filter.split("-");
        String filter_name = keyword[1];

        Page<Store> FilteredList = null;
        if (filter.contains("category")) {
            FilteredList = storeService.filterByCategory(filter_name, pageable);
        } else if (filter.contains("region")) {
            FilteredList = storeService.filterByStore_address(filter_name, pageable);
        }
        log.info("filter uid = " + uid);
        log.info(filter_name);
        log.info(filter);
        log.info(FilteredList.toString());

        model.addAttribute("filterList", FilteredList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", FilteredList.hasNext());
        model.addAttribute("hasPrev", FilteredList.hasPrevious());
        model.addAttribute("keyword", filter);

        return "rest/storelist-filter";

    }

    //category
    @GetMapping("/category/{uid}")
    public String category(@PathVariable String uid, Model model) {

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

        //user 정보
        User userEntity = userRepository.findByuid(uid).orElse(null);
        model.addAttribute("user", userEntity);

        return "rest/category";

    }

    //rest detail
    @GetMapping("/rest/{id}") //매장 상세페이지
    public String show(@PathVariable("id") Long id, Model model) {
        String uid = CustomUserDetailsService.getCurrentUserFromSecurityContext().getUid();
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        log.info("detail uid = " + uid);
        Store storeentity = storeRepository.findById(id).orElse(null);
        List<ReviewDto> reviewDtos = reviewService.reviews(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("rest", storeentity);
        model.addAttribute("reviewDtos", reviewDtos);

        //user 정보
        User userEntity = userRepository.findByuid(uid).orElse(null);
        model.addAttribute("user", userEntity);

        model.addAttribute("kakaoApiKey", kakaoApiKey);

        // 3. 뷰 페이지 반환하기
        return "rest/detail";

    }


}
