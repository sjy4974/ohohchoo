package com.ohohchoo.user;

import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * DB에 정상적으로 CRUD되는지 test
 */
@SpringBootTest
public class UserDBTest {
    @Autowired
    private UserService userService;


    @Test @DisplayName("INSERT test")
    void saveUserTest() {
        User user = new User("tempName", "tempEmail@temp.com", "male");
        userService.save(user);
    }

    @Test @DisplayName("SELECT test")
    void findByEmailTest() {
        Optional<User> user = userService.findByEmail("tempEmail@temp.com");
        if(user.isEmpty()) System.out.println("해당 email을 가진 사용자는 없습니다.");
        else System.out.println(user);
    }

    @Test @DisplayName("DELETE test")
    void deleteByIdTest() {
        int id = 1;
        userService.deleteByUserId(id);
    }
}
