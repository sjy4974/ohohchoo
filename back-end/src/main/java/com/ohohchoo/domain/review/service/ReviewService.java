package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Review;
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
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    /**
     * 리뷰 작성
     * @param reviewDto
     * @return
     */
    @Transactional
    public Long write(Long userId, ReviewWriteRequestDto reviewDto){

        // id 값으로 유저 조회.  없으면 UserNotFoundException 발생
        Optional<User> getUser = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("user not found. id = "+userId)));

        // review 엔티티 생성
        Review review = Review.builder()
                .user(getUser.get())
                .content(reviewDto.getContent())
                .address(reviewDto.getAddress())
                .build();

        return reviewRepository.save(review).getId();
    }


}
