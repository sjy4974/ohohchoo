package com.ohohchoo.domain.userLocations.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@IdClass(userLocation.class)
public class userLocation implements Serializable {
    @Id
    private Integer userId;
    @Id
    private Integer locationCode;
}
