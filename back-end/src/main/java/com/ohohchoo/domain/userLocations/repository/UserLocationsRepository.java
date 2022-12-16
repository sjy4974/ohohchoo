package com.ohohchoo.domain.userLocations.repository;

import com.ohohchoo.domain.userLocations.Entity.UserLocations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationsRepository extends JpaRepository<UserLocations, UserLocations> {
}
