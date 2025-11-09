package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.AmenityDto;
import com.codekod.rap.property_service.model.Amenity;

import java.util.List;
import java.util.Set;

public interface AmenityService {

    public List<AmenityDto> fetchAllAmenities();

    public List<AmenityDto> getAllAmenitiesByIds(List<Long> amenityIds);

    List<Amenity> loadAmenitiesByIds(List<Long> amenityIds);
}
