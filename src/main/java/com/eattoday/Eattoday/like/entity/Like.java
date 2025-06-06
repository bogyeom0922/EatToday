package com.eattoday.Eattoday.like.entity;

import com.eattoday.Eattoday.like.dto.LikeDto;
import com.eattoday.Eattoday.store.entity.Store;
import com.eattoday.Eattoday.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Likes")

public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private long state;

    public static Like createLike(LikeDto likeDto, User user, Store store, long state) {
        return new Like(likeDto.getId(), user, store, state);
    }

}
