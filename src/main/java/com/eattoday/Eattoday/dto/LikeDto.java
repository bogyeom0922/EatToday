package com.eattoday.Eattoday.dto;

import com.eattoday.Eattoday.entity.Like;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LikeDto {
    private long id;
    @JsonProperty("storeId")
    private Long store_id;
    @JsonProperty("userId")
    private Long user_id;
    private Long state;

    public static LikeDto createLikeDto(Like like) {
        return new LikeDto(
                like.getId(),
                like.getStore().getId(),
                like.getUser().getId(),
                like.getState()
        );
    }


}
