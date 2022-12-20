package com.ohohchoo.domain.weather.controller;

import com.ohohchoo.domain.weather.dto.request.LocationRequest;
import com.ohohchoo.domain.weather.dto.request.WeatherRequest;
import com.ohohchoo.domain.weather.dto.response.DateTime;
import com.ohohchoo.domain.weather.dto.response.LocationData;
import com.ohohchoo.domain.weather.dto.response.WeatherData;
import com.ohohchoo.domain.weather.dto.response.WeatherRangeData;
import com.ohohchoo.domain.weather.service.DateTimeService;
import com.ohohchoo.domain.weather.service.LocationService;
import com.ohohchoo.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* city, town 정보를 기준으로 날씨 정보를 주는 Controller.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final LocationService locationService;
    private final DateTimeService dateTimeService;

    // 현재 날씨 정보 반환
    @PostMapping("/today")
    public ResponseEntity<WeatherData> getWeatherToday(@Validated @RequestBody LocationRequest reqLoc) {
        // 요청 받은 city, town의 location data 받아옴.
        LocationData locData = locationService.getLocationData(reqLoc);
        // 현재 시간 기준 baseDate, baseTime 받아옴
        DateTime baseDateTime = dateTimeService.getBaseDateTime();
        String baseDate = baseDateTime.getBaseDate();
        String baseTime = baseDateTime.getBaseTime();
        WeatherRequest wthReq = new WeatherRequest(locData.getLocationCode(), baseDate, baseTime, locData.getNx(), locData.getNy());
        WeatherData weatherToday = weatherService.getWeatherToday(wthReq);
        return new ResponseEntity<>(weatherToday, HttpStatus.OK);
    }


    // 최신버전의 3일치 날씨 예보를 반환
    @PostMapping("/hourly")
    public ResponseEntity<List<WeatherData>> getWeatherHourly(@Validated @RequestBody LocationRequest reqLoc) {
        System.out.println(reqLoc);
        // 요청 받은 city, town의 location data 받아옴.
        LocationData locData = locationService.getLocationData(reqLoc);
        // 현재 시간 기준 baseDate, baseTime 받아옴
        DateTime baseDateTime = dateTimeService.getBaseDateTime();
        String baseDate = baseDateTime.getBaseDate();
        String baseTime = baseDateTime.getBaseTime();
        WeatherRequest wthReq = new WeatherRequest(locData.getLocationCode(), baseDate, baseTime, locData.getNx(), locData.getNy());
        List<WeatherData> weatherDataList = weatherService.getWeatherHourly(wthReq);
        return new ResponseEntity<>(weatherDataList, HttpStatus.OK);
    }

    // 현재 날짜의 일교차 정보를 반환
    @PostMapping("/range")
    public ResponseEntity<WeatherRangeData> getWeatherRange(@Validated @RequestBody LocationRequest reqLoc) {
        // 요청 받은 city, town의 location data 받아옴.
        LocationData locData = locationService.getLocationData(reqLoc);
        WeatherRangeData weatherRangeData = weatherService.getWeatherRangeData(locData);
        return new ResponseEntity<>(weatherRangeData, HttpStatus.OK);
    }

}
