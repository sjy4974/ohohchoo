package com.ohohchoo.domain.userLocations.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user_locations")
@IdClass(UserLocations.class)
public class UserLocations implements Serializable {
    @Id
    private Integer userId;
    @Id
    private Integer locationCode;
    @Builder
    public UserLocations(Integer userId, Integer locationCode) {
        this.userId = userId;
        this.locationCode = locationCode;
    }
}
