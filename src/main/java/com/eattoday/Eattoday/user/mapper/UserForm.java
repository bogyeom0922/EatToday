package com.eattoday.Eattoday.user.mapper;

import com.eattoday.Eattoday.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor //lombok 추가, 이 메서드가 생성자 대체
@ToString // toString()메서드 대체
public class UserForm {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    private String uid;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String upassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String uname;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    private String role;

    // dto(userForm)에서 entity(User)로 값 넘겨줌
    public User toEntity() {
        return new User(id, uid, uname, upassword, email, role);
    }

    public static UserForm toUserFrom(User user) {
        UserForm form = new UserForm();
        form.setId(user.getId());
        form.setUid(user.getUid());
        form.setUpassword(user.getUpassword());
        form.setUname(user.getUname());
        form.setEmail(user.getEmail());

        return form;
    }
}
