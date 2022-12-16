package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import com.ohohchoo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("리뷰작성 테스트")
//    @Rollback(value = false)
    public void 리뷰작성()throws Exception {
        //given
        Long userId = createUser();
        ReviewWriteRequestDto dto = createReviewDto();
        Long savedId = reviewService.write(userId, dto);
        //when
        Review findReview = em.find(Review.class, savedId);
        //then
        assertEquals(savedId, findReview.getId());
    }

    @Test
    @DisplayName("userId로 조회한 유저가 없으면 리뷰작성 중 예외발생")
    public void 리뷰작성예외테스트()throws Exception {
        //given
        Long userId = 2L;
        ReviewWriteRequestDto reviewDto = createReviewDto();
        //when,then
        assertThrows(UserNotFoundException.class, () ->
                reviewService.write(userId, reviewDto));
    }


    private static ReviewWriteRequestDto createReviewDto() {
        return ReviewWriteRequestDto
                .builder()
                .content("테스트 내용 작성")
                .address(new Address("서울시", "강서구"))
                .build();
    }


    // 유저 생성 후 userId 반환
    private Long createUser() {
        UserJoinRequestDto dto = UserJoinRequestDto.builder()
                .email("test@gmail.com")
                .nickname("test")
                .gender("male")
                .build();
        return userService.join(dto);
    }
}