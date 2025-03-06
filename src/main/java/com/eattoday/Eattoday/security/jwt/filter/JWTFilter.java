package com.eattoday.Eattoday.security.jwt.filter;

import com.eattoday.Eattoday.security.dto.CustomUserDetails;
import com.eattoday.Eattoday.security.jwt.JWTUtil;
import com.eattoday.Eattoday.security.service.CustomUserDetailsService;
import com.eattoday.Eattoday.user.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        /*String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }*/

        Cookie[] cookies = request.getCookies();
        String authorization = null;
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        if(authorization == null /*|| !authorization.startsWith("Bearer")*/){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        System.out.println("authorization now");
        System.out.println("before substring" + authorization);
        //String token = authorization.substring(7).trim();
        String token = authorization;
        System.out.println(token);

        String userName = jwtUtil.getUserName(token);
        String role = jwtUtil.getRole(token);

        if(jwtUtil.isExpired(token)){
            System.out.println("token expired");
            Cookie expiredCookie = new Cookie("Authorization", "");
            expiredCookie.setHttpOnly(true);
            expiredCookie.setPath("/");
            expiredCookie.setMaxAge(0);
            response.addCookie(expiredCookie);


            String newToken = jwtUtil.createJwt(userName, role, 10 * 10 * 100L);
            System.out.println("New Token: " + newToken);

            // 새로운 Authorization 쿠키 설정
            Cookie newJwtCookie = new Cookie("Authorization", newToken);
            newJwtCookie.setHttpOnly(true);
            newJwtCookie.setPath("/");
            newJwtCookie.setMaxAge(60 * 6);
            response.addCookie(newJwtCookie);
            return;
        }

        User user = new User();
        user.setUid(userName);
        user.setUpassword("tempPassword");

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);

    }

}
