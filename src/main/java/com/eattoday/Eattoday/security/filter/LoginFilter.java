package com.eattoday.Eattoday.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

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

        String userName = obtainUsername(request);
        String password = obtainPassword(request);


    }

}
