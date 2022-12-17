package com.ohohchoo.domain.review.repository;

import com.ohohchoo.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    public Optional<Review> findById(Long id);
}
