package com.eattoday.Eattoday.entity;

import com.eattoday.Eattoday.dto.LikeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "likes")

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private long state;

    public static Like createLike(LikeDto likeDto, User user, Store store, long state){
        return new Like(likeDto.getId(), user, store, state);
    }
}
