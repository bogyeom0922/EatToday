package com.eattoday.Eattoday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    //main
    @GetMapping("/")
    public String home() {
        return "home";
    }

}
