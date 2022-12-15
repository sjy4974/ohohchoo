package com.ohohchoo.domain.user.service;

import com.ohohchoo.domain.user.dto.UpdateUserDto;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.repository.UserRepository;
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
    public Optional<User> findByUserId(Integer userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user;
    }

    //ID를 통해 사용자를 삭제한다.
    public void deleteByUserId(Integer userId){
        userRepository.deleteByUserId(userId);
    }

    //사용자를 등록한다.
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    //사용자 정보 변경
    public void updateUser(int userId, UpdateUserDto updateUserDto) {
        Optional<User> optional = userRepository.findByUserId(userId);
        User user = optional.get();
        //DB에서 받은 entity를 수정한다.
        user.setSensitivity(updateUserDto.getSensitivity());
        user.setGender(updateUserDto.getGender());
        user.setTimeGoOut(updateUserDto.getTimeGoOut());
        user.setTimeGoIn(updateUserDto.getTimeGoIn());
        userRepository.save(user);
    }

}
