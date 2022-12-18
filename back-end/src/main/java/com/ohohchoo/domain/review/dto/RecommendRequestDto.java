package com.ohohchoo.domain.review.dto;

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

    @Builder
    public RecommendRequestDto(Long userId, Long reviewId){
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
