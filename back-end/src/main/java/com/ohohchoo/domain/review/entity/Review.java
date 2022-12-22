package com.ohohchoo.domain.review.entity;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 200)
    private String content;

    private LocalDate regDate;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Recommend> recommends = new ArrayList<>();

    @Column(columnDefinition = "INT default 0")
    private Long likeCnt;

    @Column(columnDefinition = "INT default 0")
    private Long dislikeCnt;

    @Builder
    public Review(User user, String content, Address address) {
        this.user = user;
        this.content = content;
        this.address = address;
        this.regDate = LocalDate.now();
    }


    // 연관 관계 메서드
    public void addRecommend(Recommend recommend){
        this.recommends.add(recommend);
    }

    // likeCnt 셋팅
    public void changeLikeCnt(Long likeCnt){
        this.likeCnt = likeCnt;
    }

    // disLikeCnt 셋팅
    public void changeDislikeCnt(Long dislikeCnt){
        this.dislikeCnt = dislikeCnt;
    }


}
