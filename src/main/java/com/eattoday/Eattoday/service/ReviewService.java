package com.eattoday.Eattoday.service;

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

    //리뷰 값 가져오기
    public List<ReviewDto> reviews(Long storeId) {
        return reviewRepository.findByStoreId(storeId)
                .stream() //리스트에 저장된 요소들 하나씩 참조하며 반복 처리할 때 사용
                .map(ReviewDto::createReviewDto)
                .collect(Collectors.toList());
    }

    //리뷰 생성
    @Transactional
    public ReviewDto create(Long storeId, ReviewDto dto) {
        //1. 게시글 조회 및 예외 발생
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 생성 실패! " +
                        "대상 매장이 없습니다."));
        //2. 리뷰 엔티티 생성
        Review review = Review.createReview(dto, store);
        //3. 리뷰 엔티티를 DB에 저장
        Review created = reviewRepository.save(review);
        //4. DTO로 변환해 반환
        return ReviewDto.createReviewDto(created);
    }

    //리뷰 수정
    @Transactional
    public ReviewDto update(Long id, ReviewDto dto) {
        //1. 리뷰 조회
        Review target = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!" +
                        "대상 댓글이 없습니다."));
        //2. 리뷰 수정
        target.patch(dto);
        //3. DB로 갱신
        Review updated = reviewRepository.save(target);
        //4. 리뷰 엔티티를 DTO로 변환 및 반환
        return ReviewDto.createReviewDto(updated);
    }

    //리뷰 삭제
    public ReviewDto delete(Long id) {
        //1. 리뷰 조회 및 예외 발생
        Review target = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " + "대상이 없습니다."));

        //2. 리뷰 삭제
        reviewRepository.delete(target);

        //3. 삭제 리뷰를 DTO로 변환 및 반환
        return ReviewDto.createReviewDto(target);
    }
}