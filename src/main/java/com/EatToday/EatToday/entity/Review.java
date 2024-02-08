package com.EatToday.EatToday.entity;

import com.EatToday.EatToday.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Review")
@Getter
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
    private String userid;
    @Column
    private String body;

    public static Review createReview(ReviewDto dto, Store store) {
        //예외 발생
        if(dto.getId()!=null) {
            throw new IllegalArgumentException("리뷰 생성 실패! 리뷰의 id가 없어야 합니다.");
        }
//        if (dto.getStoreId() != store.getId()) {
//            throw new IllegalArgumentException("리뷰 생성 실패! 매장의 id가 잘못됐습니다.");
//        }
        //엔티티 생성 및 반환
        return new Review(
                dto.getId(),
                store,
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
