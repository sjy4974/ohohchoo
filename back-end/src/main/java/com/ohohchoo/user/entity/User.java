package com.ohohchoo.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nickname;
    private String email;
    private String gender;
    private Integer sensitivity;

    @Builder
    public User(String nickname, String email, String gender, Integer sensitivity){
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.sensitivity = sensitivity;
    }

    public User(String nickname, String email, String gender){
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }



}
