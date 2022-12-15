package com.ohohchoo.domain.weather.repository;

import com.ohohchoo.domain.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    // 위치, 현재 날짜, 시간 기준으로 일치하는 날씨 데이터 반환
    Weather findByLocationCodeAndBaseDateAndBaseTime
    (Integer locationCode, String baseDate, String baseTime);
}
