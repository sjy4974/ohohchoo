package com.ohohchoo.location;

import com.ohohchoo.domain.weather.entity.Location;
import com.ohohchoo.domain.weather.repository.LocationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    @DisplayName("위치 정보 가져오기")
    void getLocation() {
        String city = "서울특별시";
        String town = "관악구";
        Location loc = locationRepository.findByCityAndTown(city, town);
        Assertions.assertThat(loc.getLocationCode()==21 && loc.getNx()==59 && loc.getNy()==125);
    }

}
