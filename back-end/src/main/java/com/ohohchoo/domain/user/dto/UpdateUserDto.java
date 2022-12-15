package com.ohohchoo.domain.user.dto;

import com.ohohchoo.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {
    private String gender;
    private Integer sensitivity;
    private Integer timeGoOut;
    private Integer timeGoIn;
    @Builder
    public UpdateUserDto(String gender, Integer sensitivity, Integer timeGoOut, Integer timeGoIn) {
        this.gender = gender;
        this.sensitivity = sensitivity;
        this.timeGoOut = timeGoOut;
        this.timeGoIn = timeGoIn;
    }

}
