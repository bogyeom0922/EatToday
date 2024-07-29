package com.eattoday.Eattoday.controller;

import com.eattoday.Eattoday.dto.LikeDto;
import com.eattoday.Eattoday.service.UserService;
import com.eattoday.Eattoday.dto.UserForm;
import com.eattoday.Eattoday.entity.Review;
import com.eattoday.Eattoday.entity.Store;
import com.eattoday.Eattoday.entity.User;
import com.eattoday.Eattoday.repository.ReviewRepository;
import com.eattoday.Eattoday.repository.StoreRepository;
import com.eattoday.Eattoday.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j // 로깅확인 어노테이션
@RequiredArgsConstructor //userRepository 생성자 만들어주는 어노테이션
public class LoginController {

    //repository 객체 선언
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ReviewRepository reviewRepository;

    @Autowired
    private final StoreRepository storeRepository;

    //service 객체 선언
    @Autowired
    private final UserService userService;

    @Autowired
    private com.eattoday.Eattoday.service.LikeService likeService;

    //main
    @GetMapping("/")
    public String Home() {

        return "home";

    }

    //login
    @GetMapping("/user/login")
    public String Login() {

        return "user/login";

    }

    @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {

        // 세션을 무효화하여 로그아웃 처리
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 로그아웃 후 로그인 페이지로 리디렉션
        return new RedirectView("/user/login");

    }

    @PostMapping("/user/Signup") //POST 요청을 받았을 때, 해당 요청 값들로 구성된 객체를 검증하는 어노테이션, 각 필드의 입력값이 정해진 Validation 규칙을 따르는지 판단
    public String CreateUser(@Valid UserForm form, BindingResult bindingResult) //파라미터의 위치는 @Valid 객체 바로 뒤에 선언해야 함 (중요)
    {

        //중복 방지 기능
        if (userService.checkuidDuplicate(form.getUid())) //id 중복
        {
            log.info("uid: " + form.getUid() + " 중복");
            bindingResult.addError(new FieldError("Signup", "uid", "ID 중복"));
        }

        if (userService.checkunameDuplicate(form.getUname())) //nickname 중복
        {
            log.info("uname: " + form.getUname() + " 중복");
            bindingResult.addError(new FieldError("Signup", "uname", "Nickname 중복"));
        }

        if (userService.checkemailDuplicate(form.getEmail())) {
            log.info("email: " + form.getEmail() + " 중복");
            bindingResult.addError(new FieldError("Signup", "email", "Email 중복"));
        }

        if (bindingResult.hasErrors()) {
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

    //mypage
    @GetMapping("/{uid}") //마이페이지
    public String info(@PathVariable String uid, Model model) {

        User userEntity = userRepository.findByuid(uid).orElse(null);
        model.addAttribute("user", userEntity);

        return "userinfo/info";

    }

    @GetMapping("/{uid}/review") //마이페이지_리뷰
    public String info_review(@PathVariable String uid, Model model) {

        User userEntity = userRepository.findByuid(uid).orElse(null);
        model.addAttribute("user", userEntity);

        List<Review> reviews = reviewRepository.findByUserid(uid); //uid로 리뷰 목록 조회
        List<Store> reviewStores = new ArrayList<>(); // 각 리뷰에 해당되는 매장 정보를 저장할 리스트 생성

        for (Review review : reviews) { // 각 리뷰에 대해 매장 정보 조회 후 저장
            Store store = review.getStore();
            if (store != null) {
                reviewStores.add(store); // 매장 정보가 null이 아닌 경우 리스트에 저장
            }
        }

        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewStores", reviewStores);

        return "userinfo/info_review";

    }

    @GetMapping("api/{uid}/like") //마이페이지_즐겨찾기
    public String info_like(@PathVariable String uid, Model model) {

        log.info("detail uid = " + uid);
        List<LikeDto> myLikes = likeService.myLike(uid);
        List<Store> store = new ArrayList<>();

        for (int i = 0; i < myLikes.size(); i++) {
            Store store1 = storeRepository.findById(myLikes.get(i).getStore_id()).orElse(null);
            store.add(store1);
        }

        model.addAttribute("store", store);
        return "userinfo/info_like";

    }

}
