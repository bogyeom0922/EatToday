package com.eattoday.Eattoday.Service;

import com.eattoday.Eattoday.dto.ReviewDto;
import com.eattoday.Eattoday.repository.ReviewRepository;
import com.eattoday.Eattoday.repository.StoreRepository;
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
                .map(review -> ReviewDto.createReviewDto(review))
                .collect(Collectors.toList());
    }
}