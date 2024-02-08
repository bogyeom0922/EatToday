package com.EatToday.EatToday.api;

import com.EatToday.EatToday.dto.ReviewDto;
import com.EatToday.EatToday.entity.Review;
import com.EatToday.EatToday.entity.Store;
import com.EatToday.EatToday.repository.StoreRepository;
import com.EatToday.EatToday.repository.UserRepository;
import com.EatToday.EatToday.service.ReviewService;
import com.EatToday.EatToday.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ReviewApiController {

//    @Autowired
//    private StoreRepository storeRepository;

//    @Autowired
//    private StoreService storeService;
//
//    //GET
//    @GetMapping("/api/stores")
//    public List<Store> index() {
//        return storeService.index();
//    }
//    @GetMapping("/api/stores/{id}")
//    public Store show(@PathVariable Long id) {
//        return storeService.show(id);
//    }


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

    //3. 수정
    @PatchMapping("/api/reviews/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @RequestBody ReviewDto dto) {
        //서비스에 위임
        ReviewDto updatedDto = reviewService.update(id, dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    //4. 삭제
    @DeleteMapping("api/reviews/{id}")
    public ResponseEntity<ReviewDto> delete(@PathVariable Long id) {
        //서비스에 위임
        ReviewDto deletedDto = reviewService.delete(id);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}