package com.ohohchoo.domain.userLocations.service;

import com.ohohchoo.domain.userLocations.Entity.UserLocations;
import com.ohohchoo.domain.userLocations.repository.UserLocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Integer> getAllLocations(int userId) {
        List<UserLocations> userLocationsList = userLocationsRepository.findAllByUserId(userId);
        List<Integer> locationsList = new ArrayList<>();
        userLocationsList.stream().forEach(userLocations -> locationsList.add(userLocations.getLocationCode()));
        return locationsList;
    }
}
