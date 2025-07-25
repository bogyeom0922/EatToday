package com.eattoday.Eattoday.user.controller;

import com.eattoday.Eattoday.like.dto.LikeDto;
import com.eattoday.Eattoday.like.service.LikeService;
import com.eattoday.Eattoday.email.service.EmailSendService;
import com.eattoday.Eattoday.reservation.domain.UserReservation;
import com.eattoday.Eattoday.user.service.UserService;
import com.eattoday.Eattoday.user.mapper.UserForm;
import com.eattoday.Eattoday.review.entity.Review;
import com.eattoday.Eattoday.store.entity.Store;
import com.eattoday.Eattoday.user.domain.User;
import com.eattoday.Eattoday.review.repository.ReviewRepository;
import com.eattoday.Eattoday.store.repository.StoreRepository;
import com.eattoday.Eattoday.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j // 로깅확인 어노테이션
@RequiredArgsConstructor //userRepository 생성자 만들어주는 어노테이션
public class UserController {

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    //repository 객체 선언
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ReviewRepository reviewRepository;

    @Autowired
    private final StoreRepository storeRepository;
    @Autowired
    private EmailSendService emailSendService;

    //service 객체 선언
    @Autowired
    private final UserService userService;

    @Autowired
    private LikeService likeService;

    //login
    @GetMapping("/user/login")
    public String Login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserForm form) {

        UserForm loginResult = userService.login(form);
        //로그인 성공
        if (loginResult != null) {

            User userEntity = form.toEntity();

            log.info("Login successful for user: {}", loginResult.getUname());

            return "redirect:/category/" + loginResult.getUid();
        } //로그인 실패
        else {
            log.info("Login failed");

            return "redirect:/user/login";
        }

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

        //컨트롤러가 제대로 동작하는지 확인하기 위함
        log.info(toString());
        // System.out.println(form.toString());

        //1. DTO를 엔티티로 변환
        form.setUpassword(passwordEncoder.encode(form.getUpassword()));
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

    @GetMapping("/{uid}/review")
    public String info_review(@PathVariable String uid,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "7") int size,
                              Model model) {

        User userEntity = userRepository.findByuid(uid).orElse(null);
        model.addAttribute("user", userEntity);

        Page<Review> reviewsPage = reviewRepository.findByUserid(uid, PageRequest.of(page, size));
        List<Review> reviews = reviewsPage.getContent();
        List<Store> reviewStores = new ArrayList<>(); // 각 리뷰에 해당되는 매장 정보를 저장할 리스트 생성

        for (Review review : reviews) { // 각 리뷰에 대해 매장 정보 조회 후 저장
            Store store = review.getStore();
            if (store != null) {
                reviewStores.add(store); // 매장 정보가 null이 아닌 경우 리스트에 저장
            }
        }

        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewStores", reviewStores);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviewsPage.getTotalPages());
        model.addAttribute("previousPage", page - 1);
        model.addAttribute("nextPage", page + 1);

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

    @GetMapping("/user/findid") //아이디 찾기 뷰
    public String findidview() {
        return "user/findid";
    }

    @PostMapping("/user/findid") //아이디 찾기
    public String findid(UserForm form, Model model) {
        UserForm findidResult = userService.findid(form);

        if (findidResult != null) {
            log.info("id = " + findidResult.getUid());
            model.addAttribute("message", findidResult.getUname() + "님의 id : " + findidResult.getUid());
            return "user/findidComplete";
        } else {
            log.info("find id fail");
            return "user/findid";
        }

    }

    @PostMapping("/user/findpassword") //비밀번호 찾기
    public String findpassword(UserForm form, Model model) {
        UserForm findpasswordResult = userService.findpassword(form);

        if (findpasswordResult != null) {
            emailSendService.sendPassword(form);
            log.info("password = " + findpasswordResult.getUpassword());
            model.addAttribute("message", findpasswordResult.getUname() + "님의 password : " + findpasswordResult.getUpassword());
            return "user/findpasswordComplete";
        } else {
            log.info("find password fail");
            return "user/findid";
        }

    }

    @PostMapping("/uname/update") //닉네임 변경
    public String nicknameUpdate(@Valid UserForm userForm) {
        if (userService.checkunameDuplicate(userForm.getUname())) //nickname 중복
        {
            log.info("uname: " + userForm.getUname() + " 중복");
            return "redirect:/" + userForm.getUid();
        }


        log.info(userForm.toString());

        User userEntity = userForm.toEntity();
        log.info(userEntity.toString());
        //기존 값 가져오기
        userRepository.findByuid(userEntity.getUid()).ifPresent(target -> userRepository.save(userEntity));

        log.info("닉네임 변경 성공");
        return "redirect:/" + userEntity.getUid();

    }

    @GetMapping("/{uid}/delete") //계정 삭제
    public String deleteAccount(@PathVariable String uid) {
        log.info("계정 삭제 요청");

        //1. 삭제할 대상 가져오기
        User target = userRepository.findByuid(uid).orElse(null);
        assert target != null;
        log.info(target.toString());

        //2. 대상 엔티티 삭제
        userRepository.delete(target);

        return "redirect:/user/login";
    }

}
