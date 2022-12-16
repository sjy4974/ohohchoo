package com.ohohchoo.domain.user.repository;

import com.ohohchoo.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Embedded;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName(" id로 회원 조회 ")
    @Rollback(false)
    public void 아이디로회원조회() throws Exception {
        //given
        User user = createUser();
        //when
        em.clear(); // 영속성 컨텍스트 초기화
        User findUser = em.find(User.class, user.getId());
        //then
        assertEquals(user.getId(), findUser.getId());
    }


    @Test
    @DisplayName(" 회원 성별 수정 ")
//    @Rollback(false)
    public void 성별수정() throws Exception {
        //given
        User user = createUser();
        Long userId = user.getId();
        String gender = "female";
        em.clear();
        User findUser = em.find(User.class, userId);
        //when
        findUser.changeGender(gender);
        em.flush();
        em.clear();

        User findUser2 = em.find(User.class, userId);

        //then
        assertEquals(gender, findUser2.getGender());
    }


    // 회원 생성 메서드
    private User createUser() {
        String email = "test@naver.com";
        String nickname = "sun";

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .gender("male")
                .build();
        em.persist(user);
        return user;
    }
}