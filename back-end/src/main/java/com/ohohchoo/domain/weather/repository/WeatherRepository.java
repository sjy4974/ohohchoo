package com.ohohchoo.domain.weather.repository;

import com.ohohchoo.domain.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    // 위치, 예보 기준 날짜, 시간에 해당하는 예보 정보 리스트 반환
    List<Weather> findAllByLocationCodeAndBaseDateAndBaseTime
    (Integer locationCode, String baseDate, String baseTime);

    // 위치, 예보 기준 날짜, 시간, 예보 날짜, 시간에 해당하는 날씨 정보 반환
    Weather findByLocationCodeAndBaseDateAndBaseTimeAndFcstDateAndFcstTime(Integer locationCode, String baseDate, String baseTime, String fcstDate, String fcstTime);

    // 위치, 예보 기준 날짜, 시간에 해당하는 정보가 있는지 확인
    boolean existsByLocationCodeAndBaseDateAndBaseTime(Integer locationCode, String baseDate, String BaseTime);

}
