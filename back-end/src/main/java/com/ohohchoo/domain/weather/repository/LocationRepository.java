package com.ohohchoo.domain.weather.repository;

import com.ohohchoo.domain.weather.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    // 시/도와 구/군/구 정보와 일치하는 장소 데이터 반환
    Location findByCityAndTown(String City, String Town);

}
