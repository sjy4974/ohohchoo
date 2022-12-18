package com.ohohchoo.domain.review.repository;

import com.ohohchoo.domain.review.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Optional<Recommend> findById(Long id);
}
