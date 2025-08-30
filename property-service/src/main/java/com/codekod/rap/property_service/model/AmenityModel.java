package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "amenities")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class AmenityModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    @Column(name = "amenity_name")
    private String amenityName;
}
