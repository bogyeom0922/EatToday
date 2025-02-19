package com.eattoday.Eattoday.security.filter;

import com.eattoday.Eattoday.dto.UserForm;
import com.eattoday.Eattoday.security.dto.CustomUserDetails;
import com.eattoday.Eattoday.security.jwt.JWTUtil;
import com.eattoday.Eattoday.user.controller.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(final AuthenticationManager authenticationManager, final JWTUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String obtainUsername(HttpServletRequest request){
        return request.getParameter("uid");
    }

    @Override
    public String obtainPassword(HttpServletRequest request){
        return request.getParameter("upassword");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper();

        LoginRequest loginRequest = null;
        try {
            loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String userName = loginRequest.uid();
        String password = loginRequest.upassword();

        System.out.println("Attempting authentication for user: " + userName);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUsername();

        String token = jwtUtil.createJwt(userId, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
