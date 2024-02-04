package com.EatToday.EatToday.service;

import com.EatToday.EatToday.repository.UserRepository;
import com.EatToday.EatToday.dto.userForm;
import com.EatToday.EatToday.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 어노테이션 userRepository
public class UserService {

    private final UserRepository userRepository;


    //로그인 서비스
    public userForm login(userForm form)
    {
        // 1. 회원이 입력 uid로 db조회
        Optional<User> userOptional = userRepository.findByuid(form.getUid());



        if(userOptional.isPresent()) //조회 결과가 있다
        {
            User user = userOptional.get(); //optional로 감싸진 객체를 벗겨내는 작업, optional 벗겨내고 entity 객체 가져옴

            // 2. db에 있는 pw와 입력한 pw 같은지 확인
            if(user.getUpassword().equals(form.getUpassword())){
                //entity 와 dto 가져온게 일체할 경우
                //entity -> dto로 변환 후 리턴
                userForm dto = userForm.toUserFrom(user);

                return dto;
            }
            else { //비밀번호 불일치
                return null;
            }
        }
        else { //조회 결과가 없다
            return null;
        }

         /*
        return userOptional.filter(user -> user.getUpassword().equals(form.getUpassword()))
                .map(user -> userForm.touserFrom(user))
                .orElse(null);
        */
        /*.filter(user -> user.getUpassword().equals(form.getUpassword())): 사용자 정보가 존재하면, 해당 사용자의 비밀번호와 입력한 비밀번호를 비교하여 일치하는지 확인합니다.

.map(user -> userForm.touserFrom(user)): 비밀번호가 일치하면, 해당 사용자 정보를 userForm으로 변환하여 반환합니다.

.orElse(null): 사용자 정보가 없거나 비밀번호가 일치하지 않을 경우 null을 반환합니다.*/
    }

    //회원가입 중복 방지 기능
    public boolean checkuidDuplicate(String uid)
    {
        return userRepository.existsByuid(uid);
    }

    public  boolean checkunameDuplicate(String uname)
    {
        return userRepository.existsByuname(uname);
    }

    //닉네임으로 아이디 찾기 서비스
    public userForm findid(userForm form)
    {
        Optional<User> Nickname = userRepository.findByuname(form.getUname());

        if(Nickname.isPresent())//닉네임 조회 결과 있으면
        {
            User user = Nickname.get(); //엔티티로 가져와서
            userForm dto = userForm.toUserFrom(user); // dto로 변환

            return dto; //dto반환

        }
        else {
            return null;
        }

    }

    //아이디로 비밀번호 찾기 서비스
    public userForm findpassword(userForm form)
    {
        Optional<User> id = userRepository.findByuid(form.getUid()); //uid 관한 정보 가져옴
        if(id.isPresent()) //있는 아이디면
        {
            User user = id.get(); //uid 관한 정보 엔티티 가져와서
            userForm dto = userForm.toUserFrom(user); //dto로 반환

            return dto;
        }
        else
        {
            return null;
        }
    }

}
