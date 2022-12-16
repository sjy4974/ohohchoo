package com.ohohchoo.domain.user.service;

import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.entity.User;
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
        if (userRepository.findByEmail(userJoinRequestDto.getEmail()) != null) {
            throw new IllegalStateException("이미 존재 하는 회원 입니다. email =" + userJoinRequestDto.getEmail());
        }
    }

    /**
     * id로 유저 조회. 해당 유저가 존재하지 않으면 예외 발생
     *
     * @param id
     * @return
     */
    public Optional<User> findById(Integer id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다. id = " + id)));
        return user;
    }

    /**
     * email로 유저 조회. 해당 유저가 존재하지 않으면 예외 발생
     *
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 존재하지 않습니다. email = " + email)));
        return user;
    }


}
