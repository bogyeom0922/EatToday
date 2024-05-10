package com.eattoday.Eattoday.dto;

import com.eattoday.Eattoday.entity.Store;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StoreDto {
    private Long id;
    private String category;
    private String store_name;
    private String store_address;
    private String store_phone;
    private String store_img;
    private String store_star;
    private String store_time;
    private String review_content;
    private String store_menu;
    public Store toEntity(){
        Store build = Store.builder()
                .id(id)
                .category(category)
                .store_name(store_name)
                .store_address(store_address)
                .store_phone(store_phone)
                .store_img(store_img)
                .store_star(store_star)
                .store_time(store_time)
                .review_content(review_content)
                .store_menu(store_menu)
                .build();
        return build;
    }

    @Builder
    public StoreDto(long id,String category, String store_name, String store_address,String store_phone,
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
