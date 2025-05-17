package com.eattoday.Eattoday.user.service;

import com.eattoday.Eattoday.user.controller.dto.LoginRequest;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.infrastructure.JwtTokenProvider;
import com.eattoday.Eattoday.user.repository.UserRepository;
import com.eattoday.Eattoday.user.service.exception.login.ExistUserxception;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    public LoginService(UserRepository userRepository, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByuid(request.uid())
                .orElseThrow(ExistUserxception::new);
        user.checkPassword(request.upassword());
        return user;
    }
}
