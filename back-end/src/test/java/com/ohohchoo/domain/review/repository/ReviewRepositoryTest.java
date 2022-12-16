package com.ohohchoo.domain.review.repository;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.user.entity.User;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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

    @Autowired
    EntityManager em;

    @Autowired
    ReviewRepository reviewRepository;

    private static ValidatorFactory vf;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterAll
    public static void close() {
        vf.close();
    }


    // 리뷰 생성 메서드
    private Review createReview() {
        Review review = Review.builder()
                .user(createUser())
                .content("테스트용 내용작성")
                .address(new Address("서울시", "강서구"))
                .build();
        return review;
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