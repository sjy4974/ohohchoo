package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.review.dto.ReviewListResponseDto;
import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.RecommendStatus;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.review.exception.AccessDeniedException;
import com.ohohchoo.domain.review.exception.ReviewNotFoundException;
import com.ohohchoo.domain.review.repository.RecommendRepository;
import com.ohohchoo.domain.review.repository.ReviewRepository;
import com.ohohchoo.domain.user.entity.User;
import com.ohohchoo.domain.user.exception.UserNotFoundException;
import com.ohohchoo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final RecommendRepository recommendRepository;

    /**
     * 리뷰 작성
     *
     * @param reviewDto
     * @return reviewId
     */
    @Transactional
    public Long write(Long userId, ReviewWriteRequestDto reviewDto) {

        // id 값으로 유저 조회.  없으면 UserNotFoundException 발생
        Optional<User> getUser = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("user not found. id = " + userId)));

        // review 엔티티 생성
        Review review = Review.builder()
                .user(getUser.get())
                .content(reviewDto.getContent())
                .address(new Address(reviewDto.getCity(), reviewDto.getTown()))
                .build();

        return reviewRepository.save(review).getId();
    }

    /**
     * 리뷰 삭제
     *
     * @param reviewId
     */
    @Transactional
    public void delete(Long userId, Long reviewId) {

        // 해당 reviewId가 유효한지, 리뷰작성자의 userId와 넘어온 userId가 동일한지 검증
        validationCheck(userId, reviewId);

        reviewRepository.deleteById(reviewId);

    }

    /**
     * 날짜, 지역 정보를 입력받아 리뷰리스트 조회
     * @param userId
     * @param regDate
     * @param city
     * @param town
     * @return
     */
    public List<ReviewListResponseDto> getReviewsByRegDateAndAddress(Long userId, LocalDate regDate, String city, String town) {

        List<ReviewListResponseDto> result = new ArrayList<>();
        boolean isLike = false;
        boolean isDislike = false;

        for (Review review : reviewRepository.findByRegDateAndAddress_CityAndAddress_TownOrderByLikeCntDesc(regDate, city, town)) {
            // 로그인한 유저인 경우
            if (userId != null) {
                // 해당 리뷰에 유저가 공감을 눌렀는지 아닌지 판별
                // 공감이나 비공감을 눌렀다면 해당 변수의 값은 true로 초기화
                isLike = recommendRepository.existsByUserIdAndReviewIdAndStatus(userId, review.getId(), RecommendStatus.LIKE);
                System.out.println("isLike" + isLike);
                isDislike = recommendRepository.existsByUserIdAndReviewIdAndStatus(userId, review.getId(), RecommendStatus.DISLIKE);
            }
            result.add(new ReviewListResponseDto(review, isLike, isDislike));
        }

        return result;


    }

    /**
     * 삭제 검증
     *
     * @param reviewId
     * @param userId
     */
    public void validationCheck(Long userId, Long reviewId) {

        // 해당 유저가 존재 하는지 검증
        Optional<User> getUser = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("user not found. id = " + userId)));

        // 해당 리뷰가 존재하는지 검증
        Optional<Review> review = Optional.ofNullable(reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("review not found. reviewId = " + reviewId)));


        // 넘어온 userId가 리뷰 작성자의 userId와 같은지 검증
        if (review.get().getUser().getId() != getUser.get().getId()) {
            throw new AccessDeniedException("You do not have permission");
        }

    }


}
