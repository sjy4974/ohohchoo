package com.ohohchoo.user;

import com.ohohchoo.user.entity.User;
import com.ohohchoo.user.service.UserService;
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

    //INSERT test
    @Test
    void saveUserTest() {
        User user = new User("tempName", "tempEmail@temp.com", "male", 1);
        userService.save(user);
    }

    //SELECT test
    @Test
    void findByEmailTest() {
        Optional<User> user = userService.findByEmail("tempEmail@temp.com");
        if(user.isEmpty()) System.out.println("해당 email을 가진 사용자는 없습니다.");
        else System.out.println(user);
    }

    //DELETE test
    @Test
    void deleteByIdTest() {
        int id = 1;
        userService.deleteById(id);
    }
}
