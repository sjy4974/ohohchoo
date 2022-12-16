package com.ohohchoo.domain.review.service;

import com.ohohchoo.domain.review.dto.ReviewWriteRequestDto;
import com.ohohchoo.domain.review.entity.Review;
import com.ohohchoo.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 작성
     * @param reviewDto
     * @return
     */
    @Transactional
    public Long write(ReviewWriteRequestDto reviewDto){

        Review review = reviewDto.toEntity();
        
        return reviewRepository.save(review).getId();
    }


}
