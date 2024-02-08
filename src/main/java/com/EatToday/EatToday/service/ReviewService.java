package com.EatToday.EatToday.service;

import com.EatToday.EatToday.dto.ReviewDto;
import com.EatToday.EatToday.entity.Review;
import com.EatToday.EatToday.entity.Store;
import com.EatToday.EatToday.entity.User;
import com.EatToday.EatToday.repository.ReviewRepository;
import com.EatToday.EatToday.repository.StoreRepository;
import com.EatToday.EatToday.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private StoreRepository storeRepository;

    public List<ReviewDto> reviews(Long storeId) {
        /*//1. 댓글 조회
        List<Review> reviews = reviewRepository.findByStoreId(storeId);
        //2. 엔티티 -> DTO반환
        List<ReviewDto> dtos = new ArrayList<ReviewDto>();
        for (int i=0; i<reviews.size(); i++) {
            Review r = reviews.get(i);
            ReviewDto dto = ReviewDto.createReviewDto(r);
            dtos.add(dto);
        }*/
        //3. 결과 반환
        return reviewRepository.findByStoreId(storeId)
                .stream() //리스트에 저장된 요소들 하나씩 차조하며 반복 처리할 때 사용
                .map(review -> ReviewDto.createReviewDto(review))
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

    @Transactional
    public ReviewDto update(Long id, ReviewDto dto) {
        //1. 리뷰 조회
        Review target = reviewRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 수정 실패!" +
                        "대상 댓글이 없습니다."));
        //2. 리뷰 수정
        target.patch(dto);
        //3. DB로 갱신
        Review updated = reviewRepository.save(target);
        //4. 리뷰 엔티티를 DTO로 변환 및 반환
        return ReviewDto.createReviewDto(updated);
    }

    public ReviewDto delete(Long id) {
        //1. 리뷰 조회 및 예외 발생
        Review target = reviewRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상이 없습니다."));
        //2. 리뷰 삭제
        reviewRepository.delete(target);
        //3. 삭제 리뷰를 DTO로 변환 및 반환
        return ReviewDto.createReviewDto(target);
    }
}
