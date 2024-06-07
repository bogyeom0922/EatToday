package com.eattoday.Eattoday.entity;

import com.eattoday.Eattoday.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Review")
@Getter
@Setter
@AllArgsConstructor //생성자 대체 lombok
@ToString //toString()대체 lombok
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;
    @Column
    private String storeName;
    @Column
    private String userid;
    @Column
    private String body;

    public static Review createReview(ReviewDto dto, Store store) {
        //예외 발생
        if(dto.getId()!=null) {
            throw new IllegalArgumentException("리뷰 생성 실패! 리뷰의 id가 없어야 합니다.");
        }
        //엔티티 생성 및 반환
        return new Review(
                dto.getId(),
                store,
                dto.getStoreName(),
                dto.getUserid(),
                dto.getBody()
        );
    }

    public void patch(ReviewDto dto) {
        //예외 발생
        if(this.id!=dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        }
        //객체 갱신
        if(dto.getBody()!=null) {
            this.body = dto.getBody();
        }
    }
}
