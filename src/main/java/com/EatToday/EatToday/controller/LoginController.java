package com.EatToday.EatToday.controller;


import com.EatToday.EatToday.dto.userForm;
import com.EatToday.EatToday.entity.User;
import com.EatToday.EatToday.repository.UserRepository;
import com.EatToday.EatToday.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j // 로깅확인 어노테이션
@Controller
@RequiredArgsConstructor //userRepository 생성자 만들어주는 어노테이션
public class LoginController {

    //@Autowired //repository에 객체 주입 = DI
    private final UserRepository userRepository; //repository 객체 선언

    //@Autowired
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/user/login") //처음 localhost8080 페이지 설정
    public String login()
    {
        return "user/login";
    }

    @GetMapping("/user/Signup") //Signup 페이지 띄어줌
    public String Signup(){
        return "user/Signup";
    }


    @PostMapping("/user/Signup") //POST 요청을 받았을 때, 해당 요청 값들로 구성된 객체를 검증하는 어노테이션, 각 필드의 입력값이 정해진 Validation 규칙을 따르는지 판단
    public String Createuser(@Valid userForm form, BindingResult bindingResult) //파라미터의 위치는 @Valid 객체 바로 뒤에 선언해야 함 (중요)
    {

        //위에 로깅 어노테이션 추가함으로써 아래 코드 필요없어짐, 아래 코드는 컨트롤러가 제대로 동작하는지 확인하기 위함
        log.info(toString());
       // System.out.println(form.toString());

        //1. DTO를 엔티티로 변환
        User user = form.toEntity();
        log.info(user.toString());

        //2. repository로 엔티티를 DB에 저장
        User saved = userRepository.save(user); //entity(user) 객체 반환받아서 saved 객체에 반환
        log.info(saved.toString());

        //중복 방지 기능
        if(userService.checkuidDuplicate(form.getUid()))
        {
            log.info("uid: "+ form.getUid()+" 중복");
        }

        if(userService.checkunameDuplicate(form.getUname()))
        {
                log.info("uname: "+form.getUname()+" 중복");
        }

        if(bindingResult.hasErrors()) { //만약 에러 위에서 에러 있으면
            return "user/Signup";
        }

        return "user/login";
    }

    @GetMapping("/category")//getmapping(/login)-> return login 받아옴, url 요청 받는것
    public String category(){ //Model model 로 model 객체 받아옴, login html에 있는 uname 채울 수 있음

        return "category";
    }

    //로그인 처리 로직 , 기본적으로 login 할 때 postmapping 사용, Security 쓰면 알아서 postmapping 해주기 때문에 getmapping만 썼던 것임
    @PostMapping("/login")
    public String login(userForm form, HttpSession session, Model model)
    {
        userForm loginResult = userService.login(form);

        if(loginResult != null)
        {
            //login 성공
            session.setAttribute("uname",loginResult.getUname());
            model.addAttribute("uname",loginResult.getUname());
            log.info("Login successful for user: {}", loginResult.getUname());
            return "category";
        }
        else {
            //login 실패
            log.info("Login failed");
            return "user/login";
        }


    }



}
