package com.ohohchoo.weather;

import com.ohohchoo.domain.weather.dto.request.WeatherRequest;
import com.ohohchoo.domain.weather.dto.response.DateTime;
import com.ohohchoo.domain.weather.dto.response.LocationData;
import com.ohohchoo.domain.weather.dto.response.WeatherData;
import com.ohohchoo.domain.weather.dto.response.WeatherRangeData;
import com.ohohchoo.domain.weather.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class WeatherTest {

    @Autowired
    WeatherService weatherService;

        @Test
        @DisplayName("현재 온도 정보 반환하기")
        void getWeatherToday() {
            WeatherRequest wthReq = new WeatherRequest(1, "20221218", "2300", "60", "127");
            WeatherData weatherToday = weatherService.getWeatherToday(wthReq);
            System.out.println(weatherToday);
        }

        @Test
        @DisplayName("온도 정보 리스트 반환하기")
        void getWeatherList() {
            WeatherRequest wthReq = new WeatherRequest(1, "20221218", "2300", "60", "127");
            List<WeatherData> weatherList = weatherService.getWeatherHourly(wthReq);
        for(WeatherData weather: weatherList) {
            System.out.println(weather);
        }
    }

    @Test
    @DisplayName("일교차 정보 가져오기")
    void getWeatherRange() {
        LocationData locationData = new LocationData(1, "60", "127");
        WeatherRangeData weatherRangeData = weatherService.getWeatherRangeData(locationData);
        System.out.println(weatherRangeData);
    }

    @Test
    @DisplayName("날씨 정보 가져와서 저장하기")
    void storeWeather() {
        LocationData locData = new LocationData(1, "60", "127");
        DateTime dateTime = new DateTime("20221218", "2300");
        weatherService.insertWeather(locData, dateTime);
    }

}
