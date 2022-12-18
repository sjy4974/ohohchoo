package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.dto.RecommendRequestDto;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Recommend;
import com.ohohchoo.domain.review.entity.RecommendStatus;
import com.ohohchoo.domain.review.exception.DuplicationRecommendException;
import com.ohohchoo.domain.user.dto.UserJoinRequestDto;
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
    @DisplayName("좋아요 or 싫어요를 중복으로 요청 시 예외 발생 테스트")
    public void 좋아요_중복_예외테스트() throws Exception {
        //given
        Long userId = createUser();
        Long reviewId = createReview(userId);
        RecommendRequestDto rrdto = createRecommend(userId, reviewId);

        //when
        recommendService.likeOrDislike(rrdto);
        //then
        assertThrows(DuplicationRecommendException.class, () ->
                recommendService.likeOrDislike(rrdto),"중복 좋아요 요청시 예외 발생해야 한다.");
    }

    @Test
    @DisplayName("좋아요 or 싫어요 버튼을 누른상태에서 반대 버튼을 누를시 업데이트 테스트")
    public void 좋아요_싫어요_변경테스트()throws Exception {
        //given
        Long userId = createUser();
        Long reviewId = createReview(userId);
        RecommendRequestDto rrdto = createRecommend(userId, reviewId);
        recommendService.likeOrDislike(rrdto); // 좋아요를 누름
        RecommendRequestDto rrdto2= RecommendRequestDto.builder()
                .userId(userId)
                .reviewId(reviewId)
                .status(RecommendStatus.DISLIKE)
                .build();
        //when
        Long savedId = recommendService.likeOrDislike(rrdto2);
        Recommend findRecommend = em.find(Recommend.class, savedId);
        //then
        assertEquals(RecommendStatus.DISLIKE, findRecommend.getStatus());
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