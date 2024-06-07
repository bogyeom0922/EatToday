package com.eattoday.Eattoday.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String category;
    private String store_name;
    private String store_address;
    private String store_phone;
    private String store_img;
    private String store_star;
    private String store_time;
    private String review_content;
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

    public String getContent() {
        return this.review_content;
    }

}
