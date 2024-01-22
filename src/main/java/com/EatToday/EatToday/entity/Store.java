package com.EatToday.EatToday.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Store")
@NoArgsConstructor
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String category;
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
    @Column
    private String review_content;
    @Column
    private String store_menu;
   @Builder
    public Store(long id,String category, String store_name, String store_address,String store_phone,
                       String store_img,String store_star, String store_time,String review_content, String store_menu){
        this.id = id;
        this.category = category;
        this.store_name = store_name;
        this.store_address = store_address;
        this.store_phone = store_phone;
        this.store_img = store_img;
        this.store_star = store_star;
        this.store_time = store_time;
        this.review_content = review_content;
        this.store_menu = store_menu;
    }


}
