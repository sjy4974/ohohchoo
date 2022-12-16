package com.ohohchoo.domain.userLocations.controller;

import com.ohohchoo.domain.userLocations.Entity.UserLocations;
import com.ohohchoo.domain.userLocations.service.UserLocationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-locations")
public class UserLocationsController {
    private final UserLocationsService userLocationsService;

    //사용자 즐겨찾기 지역 추가
    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody UserLocations userLocations) {
        userLocationsService.addLocation(userLocations);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //사용자 즐겨찾기 지역 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteLocation(@RequestBody UserLocations userLocations) {
        userLocationsService.deleteLocation(userLocations);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
