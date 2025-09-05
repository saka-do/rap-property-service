package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "amenities")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Amenity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    @Column(name = "amenity_name")
    private String amenityName;

    @ManyToMany(mappedBy = "amenities", fetch = FetchType.LAZY)
    private Set<Listing> listings = new HashSet<>();


}
