package com.eattoday.Eattoday.service;

import com.eattoday.Eattoday.dto.UserForm;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
public class EmailSendService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public void sendPassword(UserForm form) {
        // 1. 클라이언트가 아이디를 입력했을 때 해당 아이디로 사용자 이메일을 조회합니다.
        Optional<User> Email = userRepository.findByuid(form.getEmail());

        Optional<User> password = userRepository.findByuid(form.getUpassword());

        if (Email.isEmpty()) {
            String uEmail = String.valueOf(Email.get());
            String upassword = String.valueOf(password.get());
            String subject = "Password";
            String message = "Your current password is: " + upassword;
            emailService.sendSimpleMessage(uEmail, subject, message);
            log.info(uEmail+"로 password: "+upassword+"보냄");
        }

    }
}
