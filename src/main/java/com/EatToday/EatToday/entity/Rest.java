package com.EatToday.EatToday.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Store")
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Rest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    // auto_increment 사용해서 설정한 값 (자동생성 전략 설정)
    private Long id;
    @Column
    private String store_name;
    @Column
    private String store_address;
    @Column
    private String store_phone;
    @Column
    private String store_img;
    @Column
    private String store_star;
    @Column
    private String store_time;
}
