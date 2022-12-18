package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.dto.RecommendRequestDto;
import com.ohohchoo.domain.review.entity.Recommend;
import com.ohohchoo.domain.review.entity.Review;
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

    @Transactional
    public Long likeOrDislike(RecommendRequestDto dto) {
        validationRecommend(dto.getUserId(), dto.getReviewId());

        Optional<Recommend> recommend = recommendRepository.findByUserIdAndReviewId(dto.getUserId(), dto.getReviewId());


        return null;
    }

    /**
     * userId, reviewId 유효성 검증
     *
     * @param userId
     * @param reviewId
     */
    public void validationRecommend(Long userId, Long reviewId) {

        // 유저가 존재하는지 검증
        Optional<User> getUser = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("user not found. id = " + userId)));

        // 해당 리뷰가 존재하는지 검증
        Optional<Review> review = Optional.ofNullable(reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("review not found. reviewId = " + reviewId)));
        
    }
}
