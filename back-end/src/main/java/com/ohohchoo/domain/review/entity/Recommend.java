package com.ohohchoo.domain.review.entity;

import com.ohohchoo.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Recommend {

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    public RecommendStatus status; // LIKE, DISLIKE
    @Id
    @GeneratedValue
    @Column(name = "recommend_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Recommend(User user, Review review, RecommendStatus status) {
        this.user = user;
        this.review = review;
        this.status = status;
    }

    // 비즈니스 로직 //

    /**
     * like로 status 업데이트
     */
    public void changeLike(){
        this.status = RecommendStatus.LIKE;
    }

    /**
     * dislike로 status 업데이트
     */
    public void changeDislike(){
        this.status = RecommendStatus.DISLIKE;
    }

}
