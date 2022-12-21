package com.ohohchoo.domain.weather.controller;

import com.ohohchoo.domain.weather.dto.request.LocationRequest;
import com.ohohchoo.domain.weather.dto.request.OutTimeRequest;
import com.ohohchoo.domain.weather.dto.request.OutTimeWeatherRequest;
import com.ohohchoo.domain.weather.dto.request.WeatherRequest;
import com.ohohchoo.domain.weather.dto.response.*;
import com.ohohchoo.domain.weather.service.DateTimeService;
import com.ohohchoo.domain.weather.service.LocationService;
import com.ohohchoo.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    @PostMapping("/today")
    public ResponseEntity<WeatherData> getWeatherToday(@Validated @RequestBody LocationRequest reqLoc) {
        // 요청 받은 city, town의 location data 받아옴.
        LocationData locData = locationService.getLocationData(reqLoc);
        // 현재 시간 기준 baseDate, baseTime 받아옴
        DateTime baseDateTime = dateTimeService.getCurrBaseDateTime();
        String baseDate = baseDateTime.getBaseDate();
        String baseTime = baseDateTime.getBaseTime();
        WeatherRequest wthReq = new WeatherRequest(locData.getLocationCode(), baseDate, baseTime, locData.getNx(), locData.getNy());
        WeatherData weatherToday = weatherService.getWeatherToday(wthReq);
        return new ResponseEntity<>(weatherToday, HttpStatus.OK);
    }

    // 외출온도에 따른 최저, 평균온도 반환
    @Transactional
    @PostMapping("/outtime")
    public ResponseEntity<OutTimeTmpData> getOutTimeTmp(@Validated @RequestBody OutTimeWeatherRequest outTimeWthReq) {
        LocationData locData = locationService.getLocationData(new LocationRequest(outTimeWthReq.getCity(), outTimeWthReq.getTown()));
        DateTime outBaseDateTime = dateTimeService.getOutBaseDateTime(outTimeWthReq.getGoOutHour());
        WeatherRequest wthReq = new WeatherRequest(locData.getLocationCode(), outBaseDateTime.getBaseDate(), outBaseDateTime.getBaseTime(), locData.getNx(), locData.getNy());
        OutTimeRequest outTimeReq = new OutTimeRequest(outTimeWthReq.getGoOutHour(), outTimeWthReq.getGoInHour());
        OutTimeTmpData outTimeTmp = weatherService.getOutTimeTmp(wthReq, outTimeReq);
        return new ResponseEntity<>(outTimeTmp, HttpStatus.OK);
    }

    // 최신버전의 3일치 날씨 예보를 반환
    @Transactional
    @PostMapping("/hourly")
    public ResponseEntity<List<WeatherData>> getWeatherHourly(@Validated @RequestBody LocationRequest reqLoc) {
        // 요청 받은 city, town의 location data 받아옴.
        LocationData locData = locationService.getLocationData(reqLoc);
        // 현재 시간 기준 baseDate, baseTime 받아옴
        DateTime baseDateTime = dateTimeService.getCurrBaseDateTime();
        String baseDate = baseDateTime.getBaseDate();
        String baseTime = baseDateTime.getBaseTime();
        WeatherRequest wthReq = new WeatherRequest(locData.getLocationCode(), baseDate, baseTime, locData.getNx(), locData.getNy());
        List<WeatherData> weatherDataList = weatherService.getWeatherHourly(wthReq);
        return new ResponseEntity<>(weatherDataList, HttpStatus.OK);
    }

    // 현재 날짜의 일교차 정보를 반환
    @Transactional
    @PostMapping("/range")
    public ResponseEntity<WeatherRangeData> getWeatherRange(@Validated @RequestBody LocationRequest reqLoc) {
        // 요청 받은 city, town의 location data 받아옴.
        LocationData locData = locationService.getLocationData(reqLoc);
        WeatherRangeData weatherRangeData = weatherService.getWeatherRangeData(locData);
        return new ResponseEntity<>(weatherRangeData, HttpStatus.OK);
    }

}
