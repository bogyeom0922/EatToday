package com.eattoday.Eattoday.service;

import com.eattoday.Eattoday.dto.UserForm;
import com.eattoday.Eattoday.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    }

}
