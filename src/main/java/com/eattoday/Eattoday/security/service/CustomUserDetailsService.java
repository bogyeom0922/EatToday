package com.eattoday.Eattoday.security.service;

import com.eattoday.Eattoday.security.dto.CustomUserDetails;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUserId(username);

        if (userData == null) {
            System.out.println("user null");
            throw new UsernameNotFoundException("user 없음");
        }

        return new CustomUserDetails(userData);
    }

    public User getCurrentUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("authentication user" + customUserDetails.getUser());
            return customUserDetails.getUser();
        }

        return null;
    }

}
