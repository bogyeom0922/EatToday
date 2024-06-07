package com.eattoday.Eattoday.api;

import com.eattoday.Eattoday.Service.ReviewService;
import com.eattoday.Eattoday.dto.ReviewDto;
import com.eattoday.Eattoday.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ReviewApiController {

    @Autowired
    private ReviewService reviewService;

    //1. 조회
    @GetMapping("api/stores/{storeId}/reviews")
    public ResponseEntity<List<ReviewDto>> reviews(@PathVariable Long storeId) {
        //서비스에 위임
        List<ReviewDto> dtos = reviewService.reviews(storeId);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //2. 댓글 생성
    @PostMapping("/api/stores/{storeId}/reviews")
    public ResponseEntity<ReviewDto> create(@PathVariable Long storeId, @RequestBody ReviewDto dto) {
        //서비스에 위임
        ReviewDto createdDto = reviewService.create(storeId, dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }
}
