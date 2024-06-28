package com.eattoday.Eattoday.dto;

import com.eattoday.Eattoday.entity.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ReviewDto {
    private Long id;
    @JsonProperty("storeId")
    private Long storeId;
    private String userid;
    private String body;

    public static ReviewDto createReviewDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getStore().getId(),
                review.getUserid(),
                review.getBody()
        );
    }
}

