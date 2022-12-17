package com.ohohchoo.domain.weather.service;

import com.ohohchoo.domain.weather.dto.response.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class DateTimeService {

    public DateTime getBaseDateTime() {
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String baseDate = dateNow.format(formatter);

        LocalDateTime timeNow = LocalDateTime.now();
        int hour = timeNow.getHour();
        int minute = timeNow.getMinute();
        StringBuilder timeCal = new StringBuilder();
        // api 업데이트 기준( 2:00 부터 3시간 간격 / 기준 시간 부터 10분 이후 부터 호출 가능)
        // 11보다 작다면, 우선 맨 앞에 0을 붙여줌.
        if(hour > 1 && hour < 11) {
            timeCal.append("0");
        }
        // 현재 시가 업데이트 기준 시라면, 분을 비교하여 반환
        if(hour % 3 == 2) {
            if(minute < 10) {
                if(hour == 2){
                    timeCal.deleteCharAt(0);
                    timeCal.append("23");
                } else if(hour == 11) {
                    timeCal.append("08");
                } else {
                    timeCal.append(hour-3);
                }
            } else {
                timeCal.append(hour);
            }
        } else {
            if(hour == 1) {
                timeCal.append("23");
            } else {
                if(hour % 3 == 0) {
                    timeCal.append(hour-1);
                } else {
                    timeCal.append(hour-2);
                }
            }
        }
        timeCal.append("00");
        String baseTime = timeCal.toString();
        return new DateTime(baseDate, baseTime);
    }

}
