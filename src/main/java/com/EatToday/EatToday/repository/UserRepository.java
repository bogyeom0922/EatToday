package com.EatToday.EatToday.repository;

import com.EatToday.EatToday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {//CrudRepository 상속받음 : JPA에서 제공하는 인터페이스로 엔티티 관리 가능

    //uid로 회원 정보 조회 (select * from User_table where uid = ?)
    Optional<User> findByuid(String uid);

    Optional<User> findByuname(String uname);



    //uid 중복 검사
    boolean existsByuid(String uid);
    //uname 중복 검사
    boolean existsByuname(String uname);


}
