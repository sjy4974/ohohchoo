package com.ohohchoo.domain.review.repository;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.user.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    private static ValidatorFactory vf;
    private static Validator validator;
    @Autowired
    EntityManager em;
    @Autowired
    ReviewRepository reviewRepository;

    @BeforeAll
    public static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterAll
    public static void close() {
        vf.close();
    }

    @Test
    @DisplayName("리뷰작성 테스트")
//    @Rollback(false)
    public void 리뷰작성() throws Exception {
        //given

        Review review = createReview();
        //when
        Review findReview = reviewRepository.save(review);
        //then
        assertEquals(review.getId(), findReview.getId());
    }

    @Test
    @DisplayName(" 리뷰 내용 검증 테스트 테스트")
    public void 리뷰입력검증() throws Exception {
        //given
        // 검증할 에러 메세지
        String message = "The number of characters must be 200 characters or less.";
        String message2 = "Content is required.";
        String content = "200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200200";

        // 200자 이하 테스트
        ReviewWriteRequestDto dto = ReviewWriteRequestDto
                .builder()
                .content(content)
                .city("서울시")
                .town("강서구")
                .build();

        // 빈 문자열 테스트
        ReviewWriteRequestDto dto2 = ReviewWriteRequestDto
                .builder()
                .content("")
                .city("서울시")
                .town("강서구")
                .build();
        //when
        // 유효하지 않은경우 violations 값 존재
        Set<ConstraintViolation<ReviewWriteRequestDto>> violations = validator.validate(dto);
        Set<ConstraintViolation<ReviewWriteRequestDto>> violations2 = validator.validate(dto2);

        //then
        // 에러 메세지가 존재하는지 검증
        assertTrue(!(violations.isEmpty()));
        assertTrue(!(violations2.isEmpty()));
        // 존재하는 에러메세지 검증
        violations.forEach(err -> {
            assertEquals(err.getMessage(), message);
        });
        violations2.forEach(err -> {
            assertEquals(err.getMessage(), message2);
        });

    }


    // 리뷰 생성 메서드
    private Review createReview() {
        return Review.builder()
                .user(createUser())
                .content("테스트용 내용작성")
                .address(new Address("서울시", "강서구"))
                .build();
    }

    // 유저 생성 메서드
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