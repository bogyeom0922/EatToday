package com.eattoday.Eattoday.security;

import com.eattoday.Eattoday.security.jwt.JWTUtil;
import com.eattoday.Eattoday.user.controller.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JWTUtil JwtUtil;

    private static final String WRONG_KEY = "kdjqkwdjklasnfklasnflknqwipdwdasfsfasfqwffqw";
    private static final String KEY = "asdsafaskfsjkgqwgqwdpqwojdpwjdasldkasdklnqwddwqdasdasdasdwqfgd"
    ;

    @Test
    public void 로그인_성공_테스트() throws Exception{
        LoginRequest loginRequest = new LoginRequest("11", "11");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("Authorization"));
    }

    @Test
    public void 로그인_실패_테스트() throws Exception{
        LoginRequest loginRequest = new LoginRequest("wrongUser", "wrongPassword");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void 로그인_쿠키_실패_테스트() throws Exception{
        LoginRequest loginRequest = new LoginRequest("11", "11");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist("Authorization123"));
    }

    @Test
    public void jwt_발급_테스트() throws Exception{
        final String userName = "123";
        final Long extraSeconds = 60 * 60 * 100L;

        final String token = JwtUtil.createJwt(userName, extraSeconds);
        assertThat(token).isNotNull();
    }

    @Test
    public void 올바른_jwt_발급_테스트() throws Exception{
        final String userName = "123";
        final Long extraSeconds = 60 * 60 * 100L;

        final String token = JwtUtil.createJwt(userName, extraSeconds);
        assertThat(JwtUtil.getUserName(token)).isEqualTo(userName);
    }

    @Test
    public void 잘못된_형식_토큰으로_jwt_접근(){
        String invalidToken = SecurityTest.createInvalidSignatureJwt();

        assertThatExceptionOfType(SignatureException.class)
                .isThrownBy(() -> JwtUtil.getUserName(invalidToken))
                .withMessageContaining("JWT signature does not match");
    }

    @Test
    @DisplayName("토큰 기간 expired 되었는지 검사")
    public void checkTokenExpired(){
        String token = SecurityTest.createExpiredToken();

        assertThat(JwtUtil.isExpired(token)).isEqualTo(true);
    }

    @Test
    @DisplayName("로그인 후 얻은 쿠키를 통해 다른 api 시큐리티 인증 테스트")
    public void checkSecurityFromCookie() throws Exception{
        Cookie authorizationCookie = getLoginCookie();
        assertThat(authorizationCookie).isNotNull();

        mockMvc.perform(get("/category")
                .cookie(authorizationCookie))
                .andExpect(status().isOk());
    }

    public static String createInvalidSignatureJwt() {
        return Jwts.builder()
                .setSubject("user1")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(SignatureAlgorithm.HS256, WRONG_KEY)
                .compact();
    }

    public static String createExpiredToken(){
        return Jwts.builder()
                .setSubject("11")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 60000))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    private Cookie getLoginCookie() throws Exception{
        LoginRequest loginRequest = new LoginRequest("11", "11");

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getCookie("Authorization");
    }
}
