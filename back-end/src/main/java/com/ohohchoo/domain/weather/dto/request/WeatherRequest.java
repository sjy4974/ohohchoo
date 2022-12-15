package com.ohohchoo.domain.weather.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class WeatherRequest {
    @NotNull
    private Integer locationCode;
    @NotNull
    private Integer baseDate;
    @NotNull
    private Integer baseTime;

}
