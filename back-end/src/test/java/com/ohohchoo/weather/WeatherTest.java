package com.ohohchoo.weather;

import com.ohohchoo.domain.weather.entity.Weather;
import com.ohohchoo.domain.weather.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTest {

    @Autowired
    WeatherRepository weatherRepository;

    @Test
    @DisplayName("날씨 정보 저장하기")
    void insertWeather() {
        Weather weather = new Weather(1, 20221215, 0500, 20221216, 1200, 1, 1, 1.8, 1.8, 1.8);
        weatherRepository.save(weather);
    }

}
