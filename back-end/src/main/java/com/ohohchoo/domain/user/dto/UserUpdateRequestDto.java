package com.ohohchoo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String gender;
    private Integer sensitivity;

    @Builder
    public UserUpdateRequestDto(String gender, Integer sensitivity){
        this.gender = gender;
        this.sensitivity = sensitivity;
    }
}
