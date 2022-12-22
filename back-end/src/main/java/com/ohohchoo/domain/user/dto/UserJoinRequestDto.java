package com.ohohchoo.domain.user.dto;

import com.ohohchoo.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {
    private String email;
    private String nickname;
    private String gender;

    @Builder
    public UserJoinRequestDto(String email, String nickname, String gender){
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .gender(gender)
                .build();
    }

}
