package com.ohohchoo.domain.user.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String nickname;
    private String email;
    private String gender;
    private Integer sensitivity;
    private Integer timeGoOut;
    private Integer timeGoIn;

    @Builder
    public User(String nickname, String email, String gender){
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

}
