package com.codekod.rap.property_service.service;

import com.codekod.rap.property_service.dto.AmenityDto;

import java.util.List;

public interface AmenityService {

    public List<AmenityDto> fetchAllAmenities();

    public List<AmenityDto> getAllAmenitiesByIds(List<Long> amenityIds);
}
