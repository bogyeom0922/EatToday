package com.eattoday.Eattoday.user.service;

import com.eattoday.Eattoday.user.controller.dto.UserRequest;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.infrastructure.JwtTokenProvider;
import com.eattoday.Eattoday.user.repository.UserRepository;
import org.apache.catalina.realm.UserDatabaseRealm;
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
}
