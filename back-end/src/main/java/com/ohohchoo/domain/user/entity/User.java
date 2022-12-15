package com.ohohchoo.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 15, nullable = false)
    private String nickname;
    @Column(length = 30, nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(5) default 'male'")
    private String gender;

    @Column(columnDefinition = "INT default 1")
    private Integer sensitivity;

    @Builder
    public User(String nickname, String email, String gender) {
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

}