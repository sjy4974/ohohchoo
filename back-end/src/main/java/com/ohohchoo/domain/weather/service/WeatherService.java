package com.ohohchoo.domain.weather.service;

import com.ohohchoo.domain.weather.dto.request.WeatherRequest;
import com.ohohchoo.domain.weather.dto.response.WeatherData;
import com.ohohchoo.domain.weather.entity.Weather;
import com.ohohchoo.domain.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    // locationId, 현재 날짜, 현재 시간 기준으로 weather 정보 반환
    public WeatherData getWeatherData(WeatherRequest wthReq) {
        Weather weather = weatherRepository.findByLocationCodeAndBaseDateAndBaseTime(
                wthReq.getLocationCode(), wthReq.getBaseDate(), wthReq.getBaseTime()
        );

        return new WeatherData(weather.getFcstDate(), weather.getFcstTime(), weather.getPty(), weather.getPty(), weather.getTmp(), weather.getTmn(), weather.getTmx());
    }

    // weather 정보 입력
    public void insertWeatherData(Weather weather) {
        weatherRepository.save(weather);
    }

}
