package com.eattoday.Eattoday.controller;

import com.eattoday.Eattoday.Service.UserService;
import com.eattoday.Eattoday.dto.userForm;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j // 로깅확인 어노테이션
@RequiredArgsConstructor //userRepository 생성자 만들어주는 어노테이션
public class LoginController {

    //@Autowired //repository에 객체 주입 = DI
    @Autowired
    private final UserRepository userRepository; //repository 객체 선언
    @Autowired
    private final UserService userService;

    //main
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/user/login") //처음 localhost8080 페이지 설정
    public String login()
    {
        return "user/login";
    }

    @PostMapping("/user/Signup") //POST 요청을 받았을 때, 해당 요청 값들로 구성된 객체를 검증하는 어노테이션, 각 필드의 입력값이 정해진 Validation 규칙을 따르는지 판단
    public String Createuser(@Valid userForm form, BindingResult bindingResult) //파라미터의 위치는 @Valid 객체 바로 뒤에 선언해야 함 (중요)
    {

        //중복 방지 기능
        if(userService.checkuidDuplicate(form.getUid())) //id 중복
        {
            log.info("uid: "+ form.getUid()+" 중복");
            bindingResult.addError(new FieldError("Signup","uid","ID 중복"));
        }

        if(userService.checkunameDuplicate(form.getUname())) //nickname 중복
        {
            log.info("uname: "+form.getUname()+" 중복");
            bindingResult.addError(new FieldError("Signup","uname","Nickname 중복"));
        }

        if(userService.checkemailDuplicate(form.getEmail()))
        {
            log.info("email: "+form.getEmail()+" 중복");
            bindingResult.addError(new FieldError("Signup","email","Email 중복"));
        }

        if(bindingResult.hasErrors())
        {
            return "user/login";
        }

        //위에 로깅 어노테이션 추가함으로써 아래 코드 필요없어짐, 아래 코드는 컨트롤러가 제대로 동작하는지 확인하기 위함
        log.info(toString());
        // System.out.println(form.toString());

        //1. DTO를 엔티티로 변환
        User user = form.toEntity();
        log.info(user.toString());

        //2. repository로 엔티티를 DB에 저장
        User saved = userRepository.save(user); //entity(user) 객체 반환받아서 saved 객체에 반환
        log.info(saved.toString());

        return "user/login";
    }


}
