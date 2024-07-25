package com.eattoday.Eattoday.service;

import com.eattoday.Eattoday.dto.LikeDto;
import com.eattoday.Eattoday.entity.Like;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.LikeReposiroty;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {
    @Autowired
    LikeReposiroty likeReposiroty;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public LikeDto create(Long store_id, Long id, LikeDto likeDto){
        Store store = storeRepository.findById(store_id)
                .orElseThrow(() -> new IllegalArgumentException("좋아요 실패! " + "대상 매장이 없습니다."));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보 없음"));
        Like like = Like.createLike(likeDto, user, store, 1);
        Like liked = likeReposiroty.save(like);
        return LikeDto.createLikeDto(liked);
    }

    @Transactional
    public LikeDto storeLike(Long user_id, Long store_id) {
        Like like = likeReposiroty.findByUser_idAndStore_id(user_id, store_id);
        if(like == null) return null;
        return LikeDto.createLikeDto(like);
    }

    @Transactional
    public List<LikeDto> myLike(String user_id){
        User user = userRepository.findByuid(user_id).orElse(null);
        Long id = user.getId();
        return likeReposiroty.findLikes(id)
                .stream()
                .map(Like -> LikeDto.createLikeDto(Like))
                .collect(Collectors.toList());

    }

    @Transactional
    public LikeDto deleteLike(Long user_id, Long store_id){
        Store store = storeRepository.findById(store_id)
                .orElseThrow(()-> new IllegalArgumentException("좋아요 실패 " +
                        "대상 매장이 없습니다."));

    }

}
