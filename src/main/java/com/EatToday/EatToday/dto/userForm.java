package com.EatToday.EatToday.dto; //회원가입 dto

import com.EatToday.EatToday.entity.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor //lombok 추가, 이 메서드가 생성자 대체
@ToString // toString()메서드 대체
public class userForm {

    private Long id;
    private String uid;
    private String upassword;
    private String uname;


    // dto(userForm)에서 entity(User)로 값 넘겨줌
    public User toEntity()
    {
        return new User(null, uid,uname,upassword);
    }

    public static userForm toUserFrom(User user)
    {
        userForm form = new userForm();
        form.setId(user.getId());
        form.setUid(user.getUid());
        form.setUpassword(user.getUpassword());
        form.setUname(user.getUname());

        return form;
    }
}
