package com.ohohchoo.domain.weather.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "location")
@Entity
public class Location {

    @Id
    @Column(name="location_code")
    private Integer locationCode;

    private String city;
    private String town;
    private String nx;
    private String ny;

}
