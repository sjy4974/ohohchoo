package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Data
public class DateTime {
    private String baseDate;
    private String baseTime;
}
