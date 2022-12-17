package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeatherRangeData {
    private Double tmn;
    private Double tmx;
}
