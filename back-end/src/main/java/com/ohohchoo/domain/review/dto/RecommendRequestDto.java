package com.ohohchoo.domain.review.dto;

import com.ohohchoo.domain.review.entity.Recommend;
import com.ohohchoo.domain.review.entity.RecommendStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RecommendRequestDto {

    @NotBlank(message = "userId is required.")
    private Long userId;

    @NotBlank(message = "reviewId is required.")
    private Long reviewId;

    private RecommendStatus status;

    @Builder
    public RecommendRequestDto(Long userId, Long reviewId, RecommendStatus status){
        this.userId = userId;
        this.reviewId = reviewId;
        this.status = status;
    }
}
