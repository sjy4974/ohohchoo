package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.review.exception.AccessDeniedException;
import com.ohohchoo.domain.review.exception.ReviewNotFoundException;
import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import com.ohohchoo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void 리뷰작성() throws Exception {
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
    public void 리뷰작성예외테스트() throws Exception {
        //given
        Long userId = 2L;
        ReviewWriteRequestDto reviewDto = createReviewDto();
        //when,then
        assertThrows(UserNotFoundException.class, () ->
                reviewService.write(userId, reviewDto));
    }

    @Test
    @DisplayName(" 리뷰 삭제 검증 중 예외 발생 (userId가 없는 경우)")
    public void 리뷰삭제검증_유저아이디없음_예외발생()throws Exception {
        //given
        Long userId = 100L;
        Long savedId = createUser();
        ReviewWriteRequestDto reviewDto = createReviewDto();
        Long reviewId = reviewService.write(savedId, reviewDto);

        // 리뷰는 존재하지만, userId가 없는경우
        //when , then
        assertThrows(UserNotFoundException.class, () ->
                reviewService.validationCheck(userId, reviewId));
    }
    @Test
    @DisplayName(" 리뷰 삭제 검증 중 예외 발생 (review Id가 없는경우)")
    public void 리뷰삭제검증_리뷰아이디가없음_예외발생()throws Exception {
        //given
        Long userId = 100L;
        Long reviewId = 100L;
        // 유저는 존재하지만, 해당 리뷰가 없는 경우
        //when, then
        assertThrows(ReviewNotFoundException.class, () ->
                reviewService.validationCheck(reviewId, userId));
    }
    
    @Test
    @DisplayName(" 리뷰삭제 검증 중 예외 발생 (해당 리뷰의 작성자가 아닌경우)")
    public void 리뷰삭제검증_권한없는유저_예외발생()throws Exception {
        //given
        Long userId1 = createUser();
        UserJoinRequestDto userDto = UserJoinRequestDto.builder()
                .email("test1@gmail.com")
                .nickname("test1")
                .gender("male")
                .build();
        Long userId2 = userService.join(userDto);
        ReviewWriteRequestDto reviewDto = createReviewDto();
        Long reviewId = reviewService.write(userId1, reviewDto);

        // 작성자인 userId1이 아닌 userId2가 삭제 요청을 한경우 예외발생
        //when ,then
        assertThrows(AccessDeniedException.class, () ->
                reviewService.validationCheck(userId2, reviewId));
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    public void 리뷰삭제_성공테스트()throws Exception {
        //given
        Long userId = createUser();
        ReviewWriteRequestDto reviewDto = createReviewDto();
        Long reviewId = reviewService.write(userId, reviewDto);
        //when
        reviewService.delete(userId, reviewId);
        //then
        assertThrows(ReviewNotFoundException.class, () ->
                reviewService.delete(userId, reviewId), "리뷰 삭제를 다시 요청시 예외가 발생해야함");
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