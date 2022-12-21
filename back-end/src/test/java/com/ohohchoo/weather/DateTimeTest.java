package com.ohohchoo.weather;

import com.ohohchoo.domain.weather.dto.response.DateTime;
import com.ohohchoo.domain.weather.service.DateTimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateTimeTest {

    @Autowired
    DateTimeService dateTimeService;

    @Test
    @DisplayName("현재 날짜, 현재 시간 가져오기")
    void getCurrDateTime() {
        DateTime currBaseDateTime = dateTimeService.getCurrBaseDateTime();
        System.out.println(currBaseDateTime);
    }

    @Test
    @DisplayName("외출 시간 기준으로 baseTime 반환")
    void getOutBaseDateTime() {
        DateTime outBaseDateTime1 = dateTimeService.getOutBaseDateTime(2);
        DateTime outBaseDateTime2 = dateTimeService.getOutBaseDateTime(6);
        DateTime outBaseDateTime3 = dateTimeService.getOutBaseDateTime(20);
        System.out.println(outBaseDateTime1);
        System.out.println(outBaseDateTime2);
        System.out.println(outBaseDateTime3);
    }
    
    @Test
    @DisplayName("현재 날짜 기준으로 baseDate 반환")
    void getCurrBaseDate() {
        String currBaseDate = dateTimeService.getCurrBaseDate();
        System.out.println(currBaseDate);
    }

    @Test
    @DisplayName("입력 받은 시간, 분 기준으로 baseTime반환")
    void getBaseTime() {
        String baseTime1 = dateTimeService.getBaseTime(12, 0);
        String baseTime2 = dateTimeService.getBaseTime(2, 9);
        String baseTime3 = dateTimeService.getBaseTime(2, 10);
        System.out.println(baseTime1);
        System.out.println(baseTime2);
        System.out.println(baseTime3);

    }

}
