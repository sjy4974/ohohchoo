package com.ohohchoo.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
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

    @Column(columnDefinition = "VARCHAR(6) default 'male'")
    private String gender;

    @Column(columnDefinition = "INT default 1")
    private Integer sensitivity;

    @Builder
    public User(String nickname, String email, String gender, Integer sensitivity) {
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.sensitivity = sensitivity;
    }

    /**
     * 성별 수정
     * @param gender
     */
    public void changeGender(String gender){
        this.gender = gender;
    }

    /**
     * 온도 민감도 수정
     * @param sensitivity
     */
    public void changeSensitivity(Integer sensitivity){
        this.sensitivity = sensitivity;
    }
}