package com.ohohchoo.domain.userLocations.controller;

import com.ohohchoo.domain.userLocations.Entity.UserLocations;
import com.ohohchoo.domain.userLocations.service.UserLocationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-locations")
public class UserLocationsController {
    private final UserLocationsService userLocationsService;
    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody UserLocations userLocations) {
        userLocationsService.addLocation(userLocations);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
