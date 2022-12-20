package com.ohohchoo.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ReviewWriteRequestDto {

    // 리뷰 내용 비어있는지, 200자 이상인지 검증. validation 통과 못하면 예외 발생
    @NotBlank(message = "Content is required.")
    @Size(max = 200, message = "The number of characters must be 200 characters or less.")
    private String content;

    private String city;
    private String town;


    @Builder
    public ReviewWriteRequestDto(String content, String city, String town) {
        this.content = content;
        this.city = city;
        this.town = town;
    }




}
