package com.eattoday.Eattoday.api;

import com.eattoday.Eattoday.dto.LikeDto;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //로그를 찍기 위한 어노테이션
@RestController

public class LikeApiController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("api/{id}/{storeId}/likes")
    public ResponseEntity<LikeDto> Likes(@PathVariable Long id, @PathVariable Long storeId){

    }

}
