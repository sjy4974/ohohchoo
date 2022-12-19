package com.ohohchoo.domain.review.repository;

import com.ohohchoo.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long id);

    List<Review> findByRegDateAndAddress_CityAndAddress_TownOrderByLikeCntDesc(LocalDate regDate, String city, String town);

}
