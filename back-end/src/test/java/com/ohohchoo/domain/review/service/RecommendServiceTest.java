package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.dto.RecommendRequestDto;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Recommend;
import com.ohohchoo.domain.review.entity.RecommendStatus;
import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
import com.ohohchoo.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class RecommendServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    RecommendService recommendService;

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("좋아요 기능 테스트")
//    @Rollback(false)
    public void 좋아요_싫어요_테스트() throws Exception {
        //given
        Long userId = createUser();
        Long reviewId = createReview(userId);
        RecommendRequestDto rrdto = createRecommend(userId, reviewId);
        //when
        Long savedId = recommendService.likeOrDislike(rrdto);
        Recommend findRecommend = em.find(Recommend.class, savedId);
        //then
        assertEquals(savedId, findRecommend.getId());
    }

    @Test
    public void 좋아요_중복_예외테스트() throws Exception {
        //given

        //when

        //then
    }

    private RecommendRequestDto createRecommend(Long userId, Long reviewId) {
        return RecommendRequestDto.builder()
                .userId(userId)
                .reviewId(reviewId)
                .status(RecommendStatus.LIKE)
                .build();
    }


    private Long createReview(Long userId) {
        ReviewWriteRequestDto dto = ReviewWriteRequestDto
                .builder()
                .content("테스트 내용 작성")
                .city("서울시")
                .town("강서구")
                .build();
        return reviewService.write(userId, dto);
    }

    private Long createUser() {
        UserJoinRequestDto dto = UserJoinRequestDto.builder()
                .email("test@gmail.com")
                .nickname("test")
                .gender("male")
                .build();
        return userService.join(dto);
    }
}