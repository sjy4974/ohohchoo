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
        String baseTime = getBaseTime(hour);
        return new DateTime(baseDate, baseTime);
    }

    // (외출시간 체크용) 입력 받은 시간을 기준으로 baseDate, baseTime 반환
    public DateTime getOutBaseDateTime(int hour) {
        // 현재 날짜 기준으로 baseDate가져오기 (단, 현재 시각이 2시 이전이라면 이전 날짜가 반환됨)
        String baseDate = getCurrBaseDate();
        // 현재 시간 가져오기
        LocalDateTime timeNow = LocalDateTime.now();
        int currHour = timeNow.getHour();
        String baseTime;

        // 입력 받은 시간이 2보다 작으면, baseDateTime은 전날 23시 기준으로 가져옴
        if(hour <= 2) {
          int date = Integer.parseInt(baseDate);
          baseDate = Integer.toString(date-1);
          baseTime = "2300";
        }
        // 현재시간이 입력받은 시간보다 빠르면, 해당 시간 기준으로 baseTime 가져옴
        else if(currHour < hour) {
            baseTime = getBaseTime(currHour);
        } else {
            baseTime = getBaseTime(hour);
        }
        return new DateTime(baseDate, baseTime);
    }

    // 현재 날짜 기준으로 baseDate 반환
    public String getCurrBaseDate() {
        LocalDate date;
        // 현재 시각 기준으로 0시~2시 10분 사이라면 이전날짜를 baseDate로 잡아야함.
        LocalDateTime timeNow = LocalDateTime.now();
        int hour = timeNow.getHour();
        if(hour <= 2) {
            date = LocalDate.now().minusDays( 1 );}
        else {
            date = LocalDate.now();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(formatter);
    }

    // 입력받은 시간, 분을 기준으로 baseTime 반환
    public String getBaseTime(int hour) {
        StringBuilder timeCal = new StringBuilder();
        // api 업데이트 기준( 2:00 부터 3시간 간격)
        // 현재 시를 기준으로 이전 업데이트 시간을 가져옴
        if(hour <= 2) timeCal.append("23");
        else if(hour <= 5) timeCal.append("02");
        else if(hour <= 8) timeCal.append("05");
        else if(hour <= 11) timeCal.append("08");
        else if(hour <= 14) timeCal.append("11");
        else if(hour <= 17) timeCal.append("14");
        else if(hour <= 20) timeCal.append("17");
        else if(hour <= 23) timeCal.append("20");
        timeCal.append("00");
        return timeCal.toString();
    }

}
