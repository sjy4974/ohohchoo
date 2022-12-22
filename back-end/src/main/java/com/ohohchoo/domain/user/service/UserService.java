package com.ohohchoo.domain.user.service;

import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.dto.UserUpdateRequestDto;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import com.ohohchoo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     *
     * @param userJoinRequestDto
     * @return userId
     */
    @Transactional
    public Long join(UserJoinRequestDto userJoinRequestDto) {
        // 중복 회원 검증
        validateDuplicateUser(userJoinRequestDto);
        User user = userJoinRequestDto.toEntity();
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 중복 회원 검증
     *
     * @param userJoinRequestDto
     */
    private void validateDuplicateUser(UserJoinRequestDto userJoinRequestDto) {

        if (userRepository.findByEmail(userJoinRequestDto.getEmail()).isPresent()) {
            throw new IllegalStateException("already exist user. email = " + userJoinRequestDto.getEmail());
        }
    }

    /**
     * id로 유저 조회. 해당 유저가 존재하지 않으면 예외 발생
     *
     * @param id
     * @return
     */
    public Optional<User> findById(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("user not found. id = "+id)));
        return user;
    }

    /**
     * email로 유저 조회. 해당 유저가 존재하지 않으면 예외 발생
     *
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email) {
//        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() ->
//                new UserNotFoundException("user not found. email = "+email)));
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    /**
     * 유저 정보 수정 (성별, 온도민감도)
     * @param id
     * @param userUpdateRequestDto
     */
    public void update(Long id,UserUpdateRequestDto userUpdateRequestDto){
        Optional<User> findUser = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("user not found. id = " + id)));

        if(userUpdateRequestDto.getGender() == null && userUpdateRequestDto.getSensitivity() == null){
            throw new IllegalArgumentException("Either gender or sensitivity must be selected.");
        }
        User user = findUser.get();
        if(userUpdateRequestDto.getGender() != null){
            user.changeGender(userUpdateRequestDto.getGender());
        }
        if(userUpdateRequestDto.getSensitivity() != null){
            user.changeSensitivity(userUpdateRequestDto.getSensitivity());
        }

    }


}
