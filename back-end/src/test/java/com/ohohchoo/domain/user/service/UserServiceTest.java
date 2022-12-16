package com.ohohchoo.domain.user.service;

import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원가입 테스트")
//    @Rollback(false)
    public void 회원가입테스트() throws Exception {
        //given
        UserJoinRequestDto dto = UserJoinRequestDto.builder()
                .email("test@gmail.com")
                .nickname("test")
                .gender("male")
                .build();
        //when

        Long savedId = userService.join(dto);
        em.clear(); // 영속성 컨텍스트 초기화
        Optional<User> findUser = userService.findById(savedId);
        //then
        assertEquals(savedId, findUser.get().getId());
    }

    @Test
    @DisplayName(" 중복회원 가입 시 예외 발생 테스트")
    public void 회원가입예외발생() throws Exception {
        //given
        UserJoinRequestDto dto = UserJoinRequestDto.builder()
                .email("test@gmail.com")
                .nickname("test")
                .gender("male")
                .build();

        Long savedId = userService.join(dto);
        // 같은 email로 다시 가입하는 경우
        //when , then
        assertThrows(IllegalStateException.class, () ->
                userService.join(dto), "같은 아이디로 가입하면 예외가 발생해야 한다.");
    }

    @Test
    @DisplayName(" 회원 조회 중 없는 id, email 로 조회시 예외 발생")
    public void 회원조회_예외발생테스트()throws Exception {
        //given
        String email = "exception@gmail.com";
        Long id = 100L;
        //when then
        assertThrows(UserNotFoundException.class, () ->
                userService.findById(id), "없는 id로 조회 하면 예외가 발생해야 한다.");
        assertThrows(UserNotFoundException.class, () ->
                userService.findByEmail(email), "없는 email로 조회 하면 예외가 발생해야 한다.");

    }


}