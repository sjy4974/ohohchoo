package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeatherData {

    private Integer fcstDate;
    private Integer fcstTime;
    private Integer pty;
    private Integer sky;
    private Double tmp;
    private Double tmn;
    private Double tmx;

}
