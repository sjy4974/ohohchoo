package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeatherData {

    private String fcstDate;
    private String fcstTime;
    private Integer ptySky;
    private Double tmp;
}
