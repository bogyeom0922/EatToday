package com.eattoday.Eattoday.user.controller;

import com.eattoday.Eattoday.user.controller.dto.UserRequest;
import com.eattoday.Eattoday.user.controller.dto.UserResponse;
import com.eattoday.Eattoday.user.infrastructure.JwtTokenProvider;
import com.eattoday.Eattoday.user.mapper.UserMapper;
import com.eattoday.Eattoday.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/users/login")
    public ResponseEntity<HttpHeaders> login(@RequestBody UserRequest request) {
        UserResponse response = UserMapper.toUserResponse(loginService.login(request));
        String jwtToken = tokenProvider.createToken(response.id());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + jwtToken);
        log.info("{}님 로그인 성공.", response.uname());
        return ResponseEntity.status(HttpStatus.OK).body(httpHeaders);
    }
}
