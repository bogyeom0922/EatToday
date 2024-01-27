package com.EatToday.EatToday.api;

import com.EatToday.EatToday.dto.ReviewDto;
import com.EatToday.EatToday.repository.StoreRepository;
import com.EatToday.EatToday.repository.UserRepository;
import com.EatToday.EatToday.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewApiController {
    @Autowired
    private ReviewService reviewService;
    //1. 조회
    @GetMapping("api/store/{storeId}/review")
    public ResponseEntity<List<ReviewDto>> review(@PathVariable Long storeId) {
        //서비스에 위임
        //결과 응답
        return null;
    }
    //2. 생성
    //3. 수정
    //4. 삭제

}