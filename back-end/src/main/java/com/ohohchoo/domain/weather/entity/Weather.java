package com.ohohchoo.domain.weather.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "weather")
@Entity
public class Weather {

    @Id
    @Column(name="location_code")
    private Integer locationCode;

    @Column(name="base_date")
    private Integer baseDate;

    @Column(name="base_time")
    private Integer baseTime;

    @Column(name="fcst_date")
    private Integer fcstDate;

    @Column(name="fcst_time")
    private Integer fcstTime;

    private Integer pty;
    private Integer sky;
    private Double tmp;
    private Double tmn;
    private Double tmx;

}
