package com.ohohchoo.domain.weather.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather")
@Entity
public class Weather {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="weather_id")
    private Integer weatherId;

    @Column(name="location_code")
    private Integer locationCode;

    @Column(name="base_date")
    private String baseDate;

    @Column(name="base_time")
    private String baseTime;

    @Column(name="fcst_date")
    private String fcstDate;

    @Column(name="fcst_time")
    private String fcstTime;

    private Integer pty;
    private Integer sky;
    private Double tmp;

    @Nullable
    private Double tmn;
    @Nullable
    private Double tmx;

}
