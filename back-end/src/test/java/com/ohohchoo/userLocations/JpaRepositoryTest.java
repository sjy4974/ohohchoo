package com.ohohchoo.userLocations;

import com.ohohchoo.domain.userLocations.Entity.UserLocations;
import com.ohohchoo.domain.userLocations.repository.UserLocationsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * JpaRepository인 UserlocationsRepository에 대한
 * test 파일입니다.
 */

@SpringBootTest
@DisplayName("UserLocations Jpa Test")
public class JpaRepositoryTest {

    @Autowired
    private UserLocationsRepository userLocationsRepository;

    @Test @DisplayName("INSERT test")
    void saveTest() {
        UserLocations userLocations = UserLocations.builder()
                .userId(1)
                .locationCode(13)
                .build();
        userLocationsRepository.save(userLocations);
    }
    @Test @DisplayName("DELETE test")
    void deleteTest() {
        UserLocations userLocations = UserLocations.builder()
                .userId(1)
                .locationCode(13)
                .build();
        userLocationsRepository.delete(userLocations);
    }

}
