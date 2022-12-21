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

    // 현재 날짜, 현재 시간을 기준으로 baseDate, baseTime 반환
    public DateTime getCurrBaseDateTime() {
        String baseDate = getCurrBaseDate();
        LocalDateTime timeNow = LocalDateTime.now();
        int hour = timeNow.getHour();
        int minute = timeNow.getMinute();
        String baseTime = getBaseTime(hour, minute);
        return new DateTime(baseDate, baseTime);
    }

    // (외출시간 체크용) 입력 받은 시간을 기준으로 baseDate, baseTime 반환
    public DateTime getOutBaseDateTime(int hour) {
        // 현재 날짜 기준으로 baseDate가져오기 (단, 현재 시각이 2시 이전이라면 이전 날짜가 반환됨)
        String baseDate = getCurrBaseDate();
        // 현재 시간 가져오기
        LocalDateTime timeNow = LocalDateTime.now();
        int currHour = timeNow.getHour();
        int currMinute = timeNow.getMinute();
        String baseTime;

        // 입력 받은 시간이 2보다 작으면, baseDateTime은 전날 23시 기준으로 가져옴
        if(hour <= 2) {
          int date = Integer.parseInt(baseDate);
          baseDate = Integer.toString(date-1);
          baseTime = "2300";
        }
        // 현재시간이 입력받은 시간보다 빠르면, 해당 시간 기준으로 baseTime 가져옴
        else if(currHour < hour) {
            baseTime = getBaseTime(currHour, currMinute);
        } else {
            baseTime = getBaseTime(hour, 0);
        }
        return new DateTime(baseDate, baseTime);
    }

    // 현재 날짜 기준으로 baseDate 반환
    public String getCurrBaseDate() {
        LocalDate date;
        // 현재 시각 기준으로 0시~2시 10분 사이라면 이전날짜를 baseDate로 잡아야함.
        LocalDateTime timeNow = LocalDateTime.now();
        int hour = timeNow.getHour();
        int minute = timeNow.getMinute();
        if(hour < 2 || hour == 2 && minute < 10) {
            date = LocalDate.now().minusDays( 1 );}
        else {
            date = LocalDate.now();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(formatter);
    }

    // 입력받은 시간, 분을 기준으로 baseTime 반환
    public String getBaseTime(int hour, int minute) {
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
        return timeCal.toString();
    }

}
