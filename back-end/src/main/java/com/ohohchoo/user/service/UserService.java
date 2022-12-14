package com.ohohchoo.user.service;

import com.ohohchoo.user.entity.User;
import com.ohohchoo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    //Email을 통해 사용자를 Optional 클래스로 반환
    //있는 경우, User가 담기고, 없는 경우, 비어있는 Optional 객체가 반환된다.
    public Optional<User> findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    //ID를 통한 사용자 조회
    public Optional<User> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    //ID를 통해 사용자를 삭제한다.
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }

    //사용자를 등록한다.
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    //온도민감도 변경
    public void updateSensitivity(User user) {
        userRepository.save(user);
    }

}
