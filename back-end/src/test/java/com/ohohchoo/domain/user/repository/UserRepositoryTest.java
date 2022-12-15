package com.ohohchoo.domain.user.repository;

import com.ohohchoo.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName(" 회원 가입 검증 ")
//    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        String email = "test@naver.com";
        String nickname = "sun";
        //when
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .build();
        userRepository.save(user);

        User findUser = userRepository.findOne(user.getId());
        //then

        assertEquals(user.getId(), findUser.getId());
    }
}