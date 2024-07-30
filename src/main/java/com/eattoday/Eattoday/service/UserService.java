package com.eattoday.Eattoday.service;

import com.eattoday.Eattoday.dto.UserForm;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 어노테이션 userRepository
public class UserService {

    private final UserRepository userRepository;

    //회원가입 중복 방지 기능
    public boolean checkuidDuplicate(String uid) {
        return userRepository.existsByuid(uid);
    }

    public boolean checkunameDuplicate(String uname) {
        return userRepository.existsByuname(uname);
    }

    public boolean checkemailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    //로그인 서비스
    public UserForm login(UserForm form) {

        // 1. 회원이 입력한 uid로 db조회
        Optional<User> userOptional = userRepository.findByuid(form.getUid());

        // 2. db에 있는 pw와 입력한 pw 같은지 확인
        if (userOptional.isPresent()) //조회 결과가 있는 경우
        {
            User user = userOptional.get(); // optional 벗겨 내고 entity 객체 가져옴

            // 2. db에 있는 pw와 입력한 pw 같은지 확인
            if (user.getUpassword().equals(form.getUpassword())) {
                //entity, dto 일치할 경우
                //entity -> dto 변환 후 리턴
                UserForm dto = UserForm.toUserFrom(user);

                return dto;
            } else { //비밀번호 불일치
                return null;
            }

        } else { //조회 결과가 없는 경우
            return null;
        }

    }

    //이메일로 아이디 찾기 서비스
    public UserForm findid(UserForm form) {
        Optional<User> Email = userRepository.findByemail(form.getEmail());

        if (Email.isPresent())//닉네임 조회 결과 있으면
        {
            User user = Email.get(); //엔티티로 가져와서

            return UserForm.toUserFrom(user); //dto반환

        } else {
            return null;
        }
    }
}
