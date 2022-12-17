package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LocationData {

    private Integer locationCode;
    private Integer nx;
    private Integer ny;

}
