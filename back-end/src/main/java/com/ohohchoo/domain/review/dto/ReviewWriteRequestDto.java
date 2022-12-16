package com.ohohchoo.domain.review.dto;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ReviewWriteRequestDto {

    @NotBlank(message = "Content is required.")
    @Size(max = 200, message = "The number of characters must be 200 characters or less.")
    private String content;

    private Address address;

    @Builder
    public ReviewWriteRequestDto(String content, Address address) {
        this.content = content;
        this.address = address;
    }

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .address(address)
                .build();
    }
}
