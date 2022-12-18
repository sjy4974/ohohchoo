package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.dto.RecommendRequestDto;
import com.ohohchoo.domain.review.entity.Recommend;
import com.ohohchoo.domain.review.entity.RecommendStatus;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.review.exception.DuplicationRecommendException;
import com.ohohchoo.domain.review.exception.ReviewNotFoundException;
import com.ohohchoo.domain.review.repository.RecommendRepository;
import com.ohohchoo.domain.review.repository.ReviewRepository;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import com.ohohchoo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

    private final RecommendRepository recommendRepository;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    /**
     * 좋아요, 싫어요 save Or update
     * @param dto
     * @return
     */
    @Transactional
    public Long likeOrDislike(RecommendRequestDto dto) {

        // 유저 검증
        Optional<User> getUser = Optional.ofNullable(userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new UserNotFoundException("user not found. id = " + dto.getUserId())));

        // 해당 리뷰가 존재하는지 검증
        Optional<Review> review = Optional.ofNullable(reviewRepository.findById(dto.getReviewId()).orElseThrow(() ->
                new ReviewNotFoundException("review not found. reviewId = " + dto.getReviewId())));

        // 유저 식별자와 리뷰 식별자에 대한 recommend 값이 존재하는지 조회
        Optional<Recommend> recommend = recommendRepository.findByUserIdAndReviewId(dto.getUserId(), dto.getReviewId());

        // recommend가 null이면 객채생성해서 save후 recommendId return
        if (recommend.isEmpty()) {
            Recommend newRecommend = Recommend.builder()
                    .user(getUser.get())
                    .review(review.get())
                    .status(dto.getStatus())
                    .build();
            return recommendRepository.save(newRecommend).getId();
        }

        // 이미 존재하는 경우 꺼냄
        Recommend findRecommend = recommend.get();

        // 좋아요 or 싫어요를 누르고 또 insert요청을 한경우 (취소요청으로 들어온 경우가 아니면 예외 발생)
        if (findRecommend.getStatus().equals(dto.getStatus())) {
            throw new DuplicationRecommendException("duplicated check recommendId = " + findRecommend.getId());
        }

        // db에 값이 존재하고, dto의 status가 다른경우 change (싫어요or좋아요를 누른상태에서 반대 버튼을 누른경우)
        if (dto.getStatus().equals(RecommendStatus.LIKE)) {
            findRecommend.changeLike();
        } else {
            findRecommend.changeDisLike();
        }
        return findRecommend.getId();

    }


}
