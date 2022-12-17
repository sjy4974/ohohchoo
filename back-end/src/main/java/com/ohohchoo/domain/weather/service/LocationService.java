package com.ohohchoo.domain.weather.service;

import com.ohohchoo.domain.weather.dto.request.LocationRequest;
import com.ohohchoo.domain.weather.dto.response.LocationData;
import com.ohohchoo.domain.weather.entity.Location;
import com.ohohchoo.domain.weather.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    // city, town 값을 기준으로 location 정보 반환.
    public LocationData getLocationData(LocationRequest locReq) {
        Location loc = locationRepository.findByCityAndTown(locReq.getCity(), locReq.getTown());
        return new LocationData(loc.getLocationCode(), loc.getNx(), loc.getNy());
    }

}
