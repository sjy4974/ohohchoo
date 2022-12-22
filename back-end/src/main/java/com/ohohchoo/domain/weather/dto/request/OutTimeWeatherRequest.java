package com.ohohchoo.domain.weather.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OutTimeWeatherRequest {
    @NotNull
    private String city;
    @NotNull
    private String town;
    @NotNull
    private int goOutHour;
    @NotNull
    private int goInHour;
}
