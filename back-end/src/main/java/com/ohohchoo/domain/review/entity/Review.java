package com.ohohchoo.domain.review.entity;

import com.ohohchoo.domain.review.Address;
import com.ohohchoo.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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


    @Builder
    public Review(User user, String content, Address address) {
        this.user = user;
        this.content = content;
        this.address = address;
        this.regDate = LocalDate.now();
    }


}
