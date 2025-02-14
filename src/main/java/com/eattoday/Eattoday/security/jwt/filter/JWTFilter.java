package com.eattoday.Eattoday.security.jwt.filter;

import com.eattoday.Eattoday.security.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer")){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

    }

}
