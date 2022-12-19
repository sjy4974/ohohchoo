package com.ohohchoo.domain.review.dto;

import com.ohohchoo.domain.review.entity.RecommendStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@ToString
public class RecommendRequestDto {

    @NotNull(message = "userId is required.")
    private Long userId;

    @NotNull(message = "reviewId is required.")
    private Long reviewId;

    private RecommendStatus status;

    @Builder
    public RecommendRequestDto(Long userId, Long reviewId, RecommendStatus status) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.status = status;
    }
}
