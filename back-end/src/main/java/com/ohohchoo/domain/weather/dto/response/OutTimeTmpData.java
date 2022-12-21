package com.ohohchoo.domain.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OutTimeTmpData {

    private Double outTimeTmn; // 최저온도
    private Double outTimeAvg; // 평균온도
}
