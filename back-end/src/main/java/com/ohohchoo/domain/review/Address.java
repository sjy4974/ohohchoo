package com.ohohchoo.domain.review;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;

    private String town;

    protected Address() {
    }

    public Address(String city, String town) {
        this.city = city;
        this.town = town;
    }

}
