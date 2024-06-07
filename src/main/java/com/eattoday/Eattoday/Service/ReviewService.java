package com.eattoday.Eattoday.Service;

import com.eattoday.Eattoday.dto.ReviewDto;
import com.eattoday.Eattoday.entity.Review;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.repository.ReviewRepository;
import com.eattoday.Eattoday.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private StoreRepository storeRepository;

    public List<ReviewDto> reviews(Long storeId) {
        return reviewRepository.findByStoreId(storeId)
                .stream() //리스트에 저장된 요소들 하나씩 차조하며 반복 처리할 때 사용
                .map(ReviewDto::createReviewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto create(Long storeId, ReviewDto dto) {
        //1. 게시글 조회 및 예외 발생
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new IllegalArgumentException("리뷰 생성 실패! " +
                        "대상 매장이 없습니다."));
        //2. 리뷰 엔티티 생성
        Review review = Review.createReview(dto, store);
        //3. 리뷰 엔티티를 DB에 저장
        Review created = reviewRepository.save(review);
        //4. DTO로 변환해 반환
        return ReviewDto.createReviewDto(created);
    }
}