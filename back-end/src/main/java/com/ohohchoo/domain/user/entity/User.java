package com.ohohchoo.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(
        name = "email_unique",
        columnNames = {"email"})})
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

    @Column(columnDefinition = "INT NOT NULL default 0900")
    private Integer timeGoOut;

    @Column(columnDefinition = "INT NOT NULL default 1800")
    private Integer timeGoIn;

    @Builder
    public User(String nickname, String email, String gender) {
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    /**
     * 성별 수정
     *
     * @param gender
     */
    public void changeGender(String gender) {
        this.gender = gender;
    }

    /**
     * 온도 민감도 수정
     *
     * @param sensitivity
     */
    public void changeSensitivity(Integer sensitivity) {
        this.sensitivity = sensitivity;
    }
}