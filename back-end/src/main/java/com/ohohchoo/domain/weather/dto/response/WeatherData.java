package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeatherData {

    private String fcstDate;
    private String fcstTime;
    private Integer pty;
    private Integer sky;
    private Double tmp;
}
