package com.ohohchoo.domain.weather.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class LocationRequest {
    @NotNull
    private String city;
    @NotNull
    private String town;
}
