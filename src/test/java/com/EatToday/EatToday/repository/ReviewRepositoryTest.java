/**
        package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.entity.Review;
import com.EatToday.EatToday.entity.Store;
import com.EatToday.EatToday.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByStoreId() {
        Case1 : 990번 게시글의 모든 댓글 조회
        {
            //1. 입력 데이터 준비
            Long storeId = 4L;
            //2. 실제 데이터
            List<Review> reviews = reviewRepository.findByStoreId(storeId);
            //3. 예상 데이터
            Store store = new Store(990L,"백반","육전식당 4호점","서울 강남구 테헤란로8길 11-4 신도빌딩 1층", "02-3452-6373", "//t1.kakaocdn.net/thumb/T800x0.q80/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fplace%2FCD888E76C96A45018ABA", "4.", "일요일", "핑까 ?: 평점이 높아서 왔는데 음.. 가격도 강남 다른데 비해 비쌈. 목살 별로고 삼겹살 괜찮다가도 냄새가 남. 그리고 고기를 잘 못 구워서 안쪽 안 익혀져 있음. 항정살은 괜찮은 부위 있다가 별로인 곳 있음.+ ", "목살 (150g): 18,000, 삼겹살 (150g): 18,000, 항정살 (150g): 20,000, 육전돼지불백: 10,000, 육전떡갈비: 11,000, 육전묵사발: 7,000, : ,");
            User user = new User(1853L, "aa", "aa", "aa");
            Review a = new Review(1L, store, user, "맛있어요");
            Review b = new Review(2L, store, user, "맛있어요2");
            List<Review> expected = Arrays.asList(a, b);
            //4. 비교 및 검증
            assertEquals(expected.toString(), reviews.toString(), "988번 글의 모든 리뷰 출력!");
        }
    }
    @Test
    void findByUserId() {
    }
}

**/