package com.ohohchoo.domain.userLocations.service;

import com.ohohchoo.domain.userLocations.Entity.UserLocations;
import com.ohohchoo.domain.userLocations.repository.UserLocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLocationsService {
    private final UserLocationsRepository userLocationsRepository;
    public void addLocation(UserLocations userLocations) {
        userLocationsRepository.save(userLocations);
    }
    public void deleteLocation(UserLocations userLocations) {
        userLocationsRepository.delete(userLocations);
    }
}
