package com.ohohchoo.domain.review.dto;

import com.ohohchoo.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ReviewListResponseDto {

    private Long id;
    private String content;
    private String city;
    private String town;
    private Long likeCnt;
    private Long dislikeCnt;

    public ReviewListResponseDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.city = review.getAddress().getCity();
        this.town = review.getAddress().getTown();
        this.likeCnt = review.getLikeCnt();
        this.dislikeCnt = review.getDislikeCnt();
    }
}
